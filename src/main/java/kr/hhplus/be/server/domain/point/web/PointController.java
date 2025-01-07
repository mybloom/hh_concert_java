package kr.hhplus.be.server.domain.point.web;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.common.apiresponse.BusinessApiResponse;
import kr.hhplus.be.server.domain.point.web.dto.PointChargeRequest;
import kr.hhplus.be.server.domain.point.web.dto.PointResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointController {

    @Operation(summary = "포인트 충전", description = "포인트 충전 API")
    @PostMapping("/api/points")
    public ResponseEntity<ApiResponse<PointResponse>> chargePoint(@RequestBody PointChargeRequest request) {
        if (request.getAmount() == 0) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("Invalid amount is 0"));
        }

        return ResponseEntity.ok().body(ApiResponse.success(new PointResponse(1000)));
    }

    @Operation(summary = "포인트 조회", description = "포인트 조회 API")
    @GetMapping("/api/points")
    public ResponseEntity<BusinessApiResponse<PointResponse>> checkPoint(@RequestParam(required = true) long userId) {
        if (userId == 0) {
            return ResponseEntity
                .badRequest()
                .body(BusinessApiResponse.failure(HttpStatus.BAD_REQUEST.toString(),"Invalid userId"));
        }

        return ResponseEntity.ok().body(BusinessApiResponse.success(new PointResponse(1000)));
    }

}
