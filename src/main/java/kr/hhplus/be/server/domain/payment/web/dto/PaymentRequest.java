package kr.hhplus.be.server.domain.payment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRequest {
    private long userId;
    private long reservationId;
    private long payAmount;
}
