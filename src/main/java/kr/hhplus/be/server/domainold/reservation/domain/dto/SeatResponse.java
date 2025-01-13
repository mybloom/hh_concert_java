package kr.hhplus.be.server.domainold.reservation.domain.dto;

import kr.hhplus.be.server.domainold.reservation.domain.Seat;
import kr.hhplus.be.server.domainold.reservation.domain.SeatReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatResponse {
    private long seatId;
    private long scheduleId;
    private long seatNo;
    private SeatReserveStatus status;
    private long price;

    public static SeatResponse of(Seat seat) {
        return new SeatResponse(
            seat.getId(),
            seat.getScheduleId(),
            seat.getSeatNo(),
            seat.getStatus(),
            seat.getPrice()
        );

    }
}
