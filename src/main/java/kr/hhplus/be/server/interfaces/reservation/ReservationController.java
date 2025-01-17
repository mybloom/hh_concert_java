package kr.hhplus.be.server.interfaces.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kr.hhplus.be.server.domain.reservation.application.ReservationService;
import kr.hhplus.be.server.domain.reservation.application.SeatService;
import kr.hhplus.be.server.domain.reservation.application.result.ReservationResponse;
import kr.hhplus.be.server.domain.reservation.application.result.SeatResponse;
import kr.hhplus.be.server.interfaces.reservation.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좌석", description = "좌석 API")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final SeatService seatService;
    private final ReservationService reservationService;

    @Operation(summary = "예약 가능 좌석", description = "예약 가능 좌석 조회 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "좌석 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "스케줄을 찾을 수 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/api/reservations/schedule/{scheduleId}/seats")
    public List<SeatResponse> retrieveSeats(
        @PathVariable(name = "scheduleId") final long scheduleId) {
        List<SeatResponse> responses = seatService.retrieveAvailableSeats(scheduleId);
        return responses;
    }

    @Operation(summary = "좌석 예약", description = "좌석 예약 요청 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "예약 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "인증 토큰 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "좌석을 찾을 수 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/api/reservations")
    public ReservationResponse reserveSeat(@RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.reserve(request);
        return response;
    }

}
