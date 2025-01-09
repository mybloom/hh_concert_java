package kr.hhplus.be.server.domain.reservation.infrastructure;

import java.util.List;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByScheduleId(long scheduleId);
}
