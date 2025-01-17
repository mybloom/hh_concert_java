package kr.hhplus.be.server.domain.reservation.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.domain.SeatReserveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByScheduleId(long scheduleId);

    @Modifying
    @Query("UPDATE Seat s SET s.status = :status WHERE s.id = :seatId")
    int updateSeatStatus(@Param("seatId") Long seatId, @Param("status") SeatReserveStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.id = :seatId")
    Optional<Seat> findByIdWithLock(@Param("seatId") Long seatId);
}
