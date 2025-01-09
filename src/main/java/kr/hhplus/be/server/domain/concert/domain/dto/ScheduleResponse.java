package kr.hhplus.be.server.domain.concert.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponse {
    long scheduleId;
    LocalDate scheduleDate;
}
