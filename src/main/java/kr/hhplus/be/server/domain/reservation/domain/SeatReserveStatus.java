package kr.hhplus.be.server.domain.reservation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatReserveStatus {

    AVAILABLE("AVAILABLE", "예약 가능"),
    OCCUPIED("OCCUPIED", "좌석 점유");

    private final String status;
    private final String description;
}
