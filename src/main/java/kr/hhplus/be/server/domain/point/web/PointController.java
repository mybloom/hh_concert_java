package kr.hhplus.be.server.domain.point.web;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.domain.point.web.dto.PointChargeRequest;
import kr.hhplus.be.server.domain.point.web.dto.PointResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointController {

    @Operation(summary = "포인트 충전", description = "포인트 충전 API")
    @PostMapping("/api/points")
    public PointResponse chargePoint(@RequestBody PointChargeRequest request) {
       return null;
    }

    @Operation(summary = "포인트 조회", description = "포인트 조회 API")
    @GetMapping("/api/points")
    public PointResponse checkPoint(@RequestParam(required = true) long userId) {
        return null;
    }

}
