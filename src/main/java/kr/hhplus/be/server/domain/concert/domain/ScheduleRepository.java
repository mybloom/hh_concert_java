package kr.hhplus.be.server.domain.concert.domain;

import java.util.List;

public interface ScheduleRepository {

    List<Schedule> findAllById(long id);

    List<Schedule> findAllByConcertId(long concertId);

    List<Schedule> findSchedulesWithLimitAndOffset(long concertId, int offset, int limit);
}
