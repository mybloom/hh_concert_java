package kr.hhplus.be.server.domainold.concert.infrastructure;

import java.util.Collections;
import java.util.List;
import kr.hhplus.be.server.domainold.concert.domain.Schedule;
import kr.hhplus.be.server.domainold.concert.domain.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public List<Schedule> findAllById(long id) {
        return scheduleJpaRepository.findAllById(Collections.singleton(id));
    }

    @Override
    public List<Schedule> findAllByConcertId(long concertId) {
        return scheduleJpaRepository.findAllByConcertId(concertId);
    }

    @Override
    public List<Schedule> findSchedulesWithLimitAndOffset(long concertId, int offset, int limit) {
        return scheduleJpaRepository.findSchedulesWithLimitAndOffset(concertId, offset, limit);
    }
}
