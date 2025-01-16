package kr.hhplus.be.server.interfaces.reservation.usecase;

import kr.hhplus.be.server.domain.point.application.PointUseService;
import kr.hhplus.be.server.domain.reservation.application.PaymentService;
import kr.hhplus.be.server.domain.reservation.application.ReservationService;
import kr.hhplus.be.server.domain.reservation.application.SeatService;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentUseCase {

    private final PaymentService paymentService;
    private final PointUseService pointUseService;
    private final SeatService seatService; //todo: seatService 안쓰도록 리팩토링. reservation에 price 필드 추가.
    private final ReservationService reservationService;

    @Transactional
    public long pay(long userId, long reservationId){
        //예약 유효성 확인
        paymentService.validateReservation(reservationId);

        //좌석 가격 확인
        Reservation reservation = paymentService.retrieveReservation(reservationId);
        long seatPrice = seatService.retrieveScheduleSeatPrice(reservation.getSeatId());

        //포인트 사용
        pointUseService.use(userId, seatPrice);

        //예약 정보 변경 및 결제 정보 저장
        return paymentService.pay(userId, reservationId);
    }
}
