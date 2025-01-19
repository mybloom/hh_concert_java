package kr.hhplus.be.server.domain.reservation.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.ALREADY_PAID_RESERVATION;
import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.INVALID_RESERVATION_ID;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.reservation.domain.Payment;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.PaymentRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public void validateReservation(long reservationId) {
        //이미 있는 예약 정보인지 확인
        validateAlreadyPaid(reservationId);

        //예약 정보 유효성 확인, reservation이 5분 이내에 이뤄진 것인지 확인
        isValidTimeReservation(reservationId);
    }

    public long pay(long userId, long reservationId) {
        //예약 확정 처리
        reserveToConfirm(reservationId);

        //결제 정보 저장
        Payment payment = paymentRepository.save(Payment.createPayment(reservationId, userId));
        return payment.getId();
    }

    //todo: 예약 관련 부분 ReservationService로 이동 예정.
    public void reserveToConfirm(long reservationId) {
        Reservation reservation = retrieveReservation(reservationId);
        reservation.assignReservedStatus();
        reservationRepository.save(reservation);
    }

    public void isValidTimeReservation(long reservationId) {
        Reservation reservation = retrieveReservation(reservationId);
        reservation.validateReservationTime();
    }

    public Reservation retrieveReservation(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_RESERVATION_ID));
        return reservation;
    }

    public void validateAlreadyPaid(long reservationId) {
        paymentRepository.findByReservationId(reservationId)
            .ifPresent(payment -> {
                throw new BusinessIllegalArgumentException(ALREADY_PAID_RESERVATION);
            });
    }

}
