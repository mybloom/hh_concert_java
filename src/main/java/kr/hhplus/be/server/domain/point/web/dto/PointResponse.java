package kr.hhplus.be.server.domain.point.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointResponse {
    private long balance;

    public static PointResponse of(long balance) {
        return new PointResponse(balance);
    }
}
