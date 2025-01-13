package kr.hhplus.be.server.domainold.concert.domain;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.INVALID_CONCERT_ID;

import java.util.List;
import java.util.stream.Collectors;
import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domainold.concert.domain.dto.ConcertResponse;
import kr.hhplus.be.server.domainold.concert.domain.dto.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ScheduleRepository scheduleRepository;

    public ConcertResponse retrieveConcertSchedules(long concertId, Integer offset, int limit) {

        Concert concert = concertRepository.findById(concertId)
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_CONCERT_ID));

        List<ScheduleResponse> scheduleResponses = scheduleRepository.findSchedulesWithLimitAndOffset(concertId, offset, limit)
            .stream()
            .map(schedule -> new ScheduleResponse(schedule.getId(), schedule.getScheduleDate()))
            .collect(Collectors.toList());

        ConcertResponse concertResponse = new ConcertResponse(
            concert.getId(),
            concert.getName(),
            scheduleResponses
        );

        return concertResponse;
    }
}
