package kr.hhplus.be.server.domain.reservation.application.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long userId;
    private long reservationId;
}
