package kr.hhplus.be.server.domain.reservation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRequest {

    private long userId;
    private long concertId;
    private long performId;
    private long seatId;

}
