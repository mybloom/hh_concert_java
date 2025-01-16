package kr.hhplus.be.server.domain.reservation.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.ALREADY_PAID_RESERVATION;
import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.INVALID_RESERVATION_ID;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.reservation.domain.Payment;
import kr.hhplus.be.server.domain.reservation.repository.PaymentRepository;
import kr.hhplus.be.server.domain.reservation.application.result.PaymentRequest;
import kr.hhplus.be.server.domain.reservation.application.result.PaymentResponse;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentResponse pay(PaymentRequest request) {
        //이미 있는 예약 정보인지 확인
        paymentRepository.findByReservationId(request.getReservationId())
            .ifPresent(payment -> {
                throw new BusinessIllegalArgumentException(ALREADY_PAID_RESERVATION);
            });

        //예약 정보 유효성 확인, reservation이 5분 이내에 이뤄진 것인지 확인
        Reservation reservation = reservationRepository.findById(request.getReservationId())
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_RESERVATION_ID));
        reservation.validateReservationTime();

        Payment payment = paymentRepository.save(Payment.createPayment(request.getReservationId(), request.getUserId()));
        return new PaymentResponse(payment.getId());
    }

}
