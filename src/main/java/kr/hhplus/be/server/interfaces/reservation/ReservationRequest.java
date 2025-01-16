package kr.hhplus.be.server.interfaces.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private long userId;
    private long seatId;

}
