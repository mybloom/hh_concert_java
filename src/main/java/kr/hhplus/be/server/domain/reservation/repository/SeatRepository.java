package kr.hhplus.be.server.domain.reservation.repository;

import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.domain.SeatReserveStatus;

public interface SeatRepository {

    List<Seat> findAllByScheduleId(long scheduleId);

    Optional<Seat> findById(long seatId);

    Seat save(Seat seat);

    int updateSeatStatus(Long seatId, SeatReserveStatus status);
}
