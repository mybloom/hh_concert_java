package kr.hhplus.be.server.domainold.reservation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

    RESERVED("RESERVED", "예약 완료"),
    TEMP("TEMP", "임시 예약");

    private final String status;
    private final String description;

}
