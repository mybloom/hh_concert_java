package kr.hhplus.be.server.domain.reservation.domain;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> findAllByScheduleId(long scheduleId);

    Optional<Seat> findById(long seatId);

    Seat save(Seat seat);

    int updateSeatStatus(Long seatId, SeatReserveStatus status);
}
