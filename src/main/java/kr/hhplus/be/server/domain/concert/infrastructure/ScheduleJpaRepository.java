package kr.hhplus.be.server.domain.concert.infrastructure;

import java.util.List;
import kr.hhplus.be.server.domain.concert.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByConcertId(long concertId);

    @Query(value = "SELECT * FROM schedule WHERE concert_id = :concertId "
        + "ORDER BY schedule_date DESC LIMIT :limit OFFSET :offset",
        nativeQuery = true)
    List<Schedule> findSchedulesWithLimitAndOffset(
        @Param("concertId") long concertId,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
