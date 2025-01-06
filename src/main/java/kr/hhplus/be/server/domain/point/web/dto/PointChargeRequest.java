package kr.hhplus.be.server.domain.point.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointChargeRequest {
    private long userId;
    private long amount;
}
