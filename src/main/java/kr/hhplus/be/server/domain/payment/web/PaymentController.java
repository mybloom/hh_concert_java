package kr.hhplus.be.server.domain.payment.web;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.common.apiresponse.BusinessApiResponse;
import kr.hhplus.be.server.domain.payment.web.dto.PaymentRequest;
import kr.hhplus.be.server.domain.payment.web.dto.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Operation(summary = "결제 요청", description = "결제 요청 API")
    @PostMapping("/api/payments")
    public ResponseEntity<BusinessApiResponse<PaymentResponse>> reserveSeat(@RequestBody PaymentRequest request) {
        if (request.getReservationId() == 0) {
            return ResponseEntity
                .badRequest()
                .body(BusinessApiResponse.failure(HttpStatus.BAD_REQUEST.toString(),"Invalid reservationId"));
        }

        return ResponseEntity.ok().body(BusinessApiResponse.success(new PaymentResponse(1)));
    }
}
