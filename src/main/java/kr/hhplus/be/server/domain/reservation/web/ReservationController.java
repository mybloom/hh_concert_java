package kr.hhplus.be.server.domain.reservation.web;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import kr.hhplus.be.server.apiresponse.ApiResponse;
import kr.hhplus.be.server.domain.reservation.web.dto.ReservationRequest;
import kr.hhplus.be.server.domain.reservation.web.dto.ReservationResponse;
import kr.hhplus.be.server.domain.reservation.web.dto.SeatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReservationController {

    @Operation(summary = "예약 가능 좌석", description = "예약 가능 좌석 조회 API")
    @GetMapping("/api/reservations/seats")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> retrieveSeats(
        @RequestParam(required = true) long performId) {
        if (performId <= 0) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("Invalid performId"));
        }

        List<SeatResponse> seats = List.of(
            new SeatResponse(1),
            new SeatResponse(2),
            new SeatResponse(3)
        );
        return ResponseEntity.ok().body(ApiResponse.success(seats));
    }

    @Operation(summary = "좌석 예약", description = "좌석 예약 요청 API")
    @PostMapping("/api/reservations")
    public ResponseEntity<ApiResponse<ReservationResponse>> reserveSeat(@RequestBody ReservationRequest request) {
        if (request.getSeatId() == 50) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("already reserved seat"));
        }

        return ResponseEntity.ok().body(ApiResponse.success(new ReservationResponse(1)));
    }

}
