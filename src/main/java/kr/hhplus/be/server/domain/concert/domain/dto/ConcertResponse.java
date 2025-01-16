package kr.hhplus.be.server.domain.concert.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConcertResponse {
    long concertId;
    String concertName;
    List<ScheduleResponse> schedules;
}
