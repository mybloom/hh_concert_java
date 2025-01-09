package kr.hhplus.be.server.domain.reservation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatReserveStatus {

    AVAILABLE("AVAILABLE", "예약 가능"),
    RESERVED("RESERVED", "예약 완료");

    private final String status;
    private final String description;
}
