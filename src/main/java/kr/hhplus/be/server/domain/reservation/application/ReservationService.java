package kr.hhplus.be.server.domain.reservation.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.INVALID_SEAT_ID;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.domain.SeatReserveStatus;
import kr.hhplus.be.server.interfaces.reservation.ReservationRequest;
import kr.hhplus.be.server.domain.reservation.application.result.ReservationResponse;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.domain.reservation.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse reserve(ReservationRequest request) {
        //좌석 상태 AVAILABLE 인지 확인
        Seat seat = seatRepository.findById(request.getSeatId())
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_SEAT_ID));

        //좌석 상태 변경
        seat.occupied();

        //좌석 상태 저장
        seatRepository.updateSeatStatus(seat.getId(), SeatReserveStatus.OCCUPIED);

        //예약 정보 저장
        Reservation reservation = reservationRepository.save(
            Reservation.createTempReservation(request.getSeatId(), request.getUserId())
        );
        return new ReservationResponse(reservation.getId());
    }

}
