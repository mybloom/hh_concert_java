package kr.hhplus.be.server.domainold.payment.domain;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.ALREADY_PAID_RESERVATION;
import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.INVALID_RESERVATION_ID;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domainold.payment.domain.dto.PaymentRequest;
import kr.hhplus.be.server.domainold.payment.domain.dto.PaymentResponse;
import kr.hhplus.be.server.domainold.reservation.domain.Reservation;
import kr.hhplus.be.server.domainold.reservation.domain.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReserveRepository reserveRepository;

    public PaymentResponse pay(PaymentRequest request) {
        //이미 있는 예약 정보인지 확인
        paymentRepository.findByReservationId(request.getReservationId())
            .ifPresent(payment -> {
                throw new BusinessIllegalArgumentException(ALREADY_PAID_RESERVATION);
            });

        //예약 정보 유효성 확인, reservation이 5분 이내에 이뤄진 것인지 확인
        Reservation reservation = reserveRepository.findById(request.getReservationId())
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_RESERVATION_ID));
        reservation.validateReservationTime();

        Payment payment = paymentRepository.save(Payment.createPayment(request.getReservationId(), request.getUserId()));
        return new PaymentResponse(payment.getId());
    }

}
