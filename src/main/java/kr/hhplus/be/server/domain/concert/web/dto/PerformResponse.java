package kr.hhplus.be.server.domain.concert.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformResponse {
    long performId;
    LocalDate performDate;
}
