package kr.hhplus.be.server.domainold.point.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointChargeRequest {
    private long userId;
    private long amount;
}
