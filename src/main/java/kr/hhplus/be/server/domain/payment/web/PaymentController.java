package kr.hhplus.be.server.domain.payment.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.payment.domain.PaymentService;
import kr.hhplus.be.server.domain.payment.domain.dto.PaymentRequest;
import kr.hhplus.be.server.domain.payment.domain.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제 API", description = "콘서트 결제 API")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 요청", description = "결제 요청 API")
    @PostMapping("/api/payments")
    public PaymentResponse pay(@RequestBody PaymentRequest request) {
        return paymentService.pay(request);
    }
}
