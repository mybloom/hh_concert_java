package kr.hhplus.be.server.interfaces.point.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PointChargeRequest(
    @NotNull(message = "{userId.notnull}")
    Long userId,
    @Min(value = 1, message = "{point.charge.amount.min}")
    long amount
) {
}
