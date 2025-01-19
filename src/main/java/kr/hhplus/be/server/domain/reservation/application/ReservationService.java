package kr.hhplus.be.server.domain.reservation.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.INVALID_SEAT_ID;
import static kr.hhplus.be.server.common.exception.errorcode.IllegalStateErrorCode.SEAT_ALREADY_RESERVED;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.common.exception.BusinessIllegalStateException;
import kr.hhplus.be.server.domain.reservation.application.result.ReservationResponse;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.domain.SeatReserveStatus;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.domain.reservation.repository.SeatRepository;
import kr.hhplus.be.server.interfaces.reservation.dto.ReservationRequest;
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
        Seat seat = seatRepository.findByIdWithLock(request.getSeatId()) //todo: 확인필요. 사실 lock을 잡는게 의미가 없을 수도 있음.
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_SEAT_ID));

        //todo: 좌석 상태 확인이 필요해보임.

        //예약 정보 확인
        reservationRepository.findBySeatId(request.getSeatId())
            .ifPresent(reservation -> {
                throw new BusinessIllegalStateException(SEAT_ALREADY_RESERVED);
            });

        //좌석 상태 변경
        seat.occupied();

        //좌석 상태 저장
        seatRepository.updateSeatStatus(seat.getId(), SeatReserveStatus.OCCUPIED);

        //예약 정보 저장 //todo: 동시성은 여기에 unique key걸어서 성공한 것 같다. 데이터 없을 때 lock을 잡아야 하면 테이블 락을 걸어야 할수도 있을 것 같다.
        Reservation reservation = reservationRepository.save(
            Reservation.createTempReservation(request.getSeatId(), request.getUserId())
        );
        return new ReservationResponse(reservation.getId());
    }


}
