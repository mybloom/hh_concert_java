package kr.hhplus.be.server.domainold.reservation.infrastructure;

import java.util.List;
import kr.hhplus.be.server.domainold.reservation.domain.Seat;
import kr.hhplus.be.server.domainold.reservation.domain.SeatReserveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByScheduleId(long scheduleId);

    @Modifying
    @Query("UPDATE Seat s SET s.status = :status WHERE s.id = :seatId")
    int updateSeatStatus(@Param("seatId") Long seatId, @Param("status") SeatReserveStatus status);

}
