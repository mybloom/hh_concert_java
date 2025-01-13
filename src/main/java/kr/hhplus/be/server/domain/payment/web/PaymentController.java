package kr.hhplus.be.server.domain.payment.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "결제 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "인증 토큰 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "402", description = "결제 실패 (잔액 부족 등)",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/api/payments")
    public PaymentResponse pay(@RequestBody PaymentRequest request) {
        return paymentService.pay(request);
    }
}
