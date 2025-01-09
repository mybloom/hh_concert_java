package kr.hhplus.be.server.domain.reservation.web;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import kr.hhplus.be.server.common.apiresponse.BusinessApiResponse;
import kr.hhplus.be.server.domain.reservation.domain.SeatService;
import kr.hhplus.be.server.domain.reservation.domain.dto.ReservationRequest;
import kr.hhplus.be.server.domain.reservation.domain.dto.ReservationResponse;
import kr.hhplus.be.server.domain.reservation.domain.dto.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final SeatService seatService;

    @Operation(summary = "예약 가능 좌석", description = "예약 가능 좌석 조회 API")
    @GetMapping("/api/reservations/schedule/{scheduleId}/seats")
    public List<SeatResponse> retrieveSeats(
        @PathVariable(name = "scheduleId") final long scheduleId) {
        List<SeatResponse> responses = seatService.retrieveAvailableSeats(scheduleId);
        return responses;
    }

    @Operation(summary = "좌석 예약", description = "좌석 예약 요청 API")
    @PostMapping("/api/reservations")
    public ResponseEntity<BusinessApiResponse<ReservationResponse>> reserveSeat(@RequestBody ReservationRequest request) {
        if (request.getSeatId() == 50) {
            return ResponseEntity
                .badRequest()
                .body(BusinessApiResponse.failure(HttpStatus.BAD_REQUEST.toString(),"already reserved seat"));
        }

        return ResponseEntity.ok().body(BusinessApiResponse.success(new ReservationResponse(1)));
    }

}
