package kr.hhplus.be.server.domain.point.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointResponse {
    private long balance;

    public static PointResponse of(long balance) {
        return new PointResponse(balance);
    }
}
