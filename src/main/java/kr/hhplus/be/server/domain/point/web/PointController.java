package kr.hhplus.be.server.domain.point.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.point.domain.PointService;
import kr.hhplus.be.server.domain.point.web.dto.PointChargeRequest;
import kr.hhplus.be.server.domain.point.web.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포인트", description = "포인트 API")
@RequiredArgsConstructor
@RestController
public class PointController {

    private final PointService pointService;

    @Operation(summary = "포인트 충전", description = "포인트 충전 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "포인트가 성공적으로 충전됨",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PointResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "토큰 인증 실패",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/api/points")
    public PointResponse chargePoint(@RequestBody PointChargeRequest request) {
        long balance = pointService.charge(request.getUserId(), request.getAmount());
        return PointResponse.of(balance);
    }

    @Operation(summary = "포인트 조회", description = "포인트 조회 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "포인트 잔액 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PointResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "인증 토큰 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/api/points")
    public PointResponse checkPoint(@RequestParam(name = "userId", required = true) long userId) {
        long balance = pointService.getBalance(userId);
        return PointResponse.of(balance);
    }

}
