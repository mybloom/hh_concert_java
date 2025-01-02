package kr.hhplus.be.server.domain.concert.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import kr.hhplus.be.server.apiresponse.ApiResponse;
import kr.hhplus.be.server.domain.concert.web.dto.PerformResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Response Concert", description = "Response Concert API")
@RestController
public class ConcertController {

    @Operation(summary = "콘서트 예약 가능 날짜", description = "콘서트 예약 가능 날짜 조회 API")
    @GetMapping("/api/concerts/performs")
    public ResponseEntity<ApiResponse<List<PerformResponse>>> retrieveConcertPerforms(@RequestParam(required = true) long concertId) {
        if (concertId <= 0) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("Invalid concertId"));
        }

        List<PerformResponse> concertList = List.of(
            new PerformResponse(1, LocalDate.of(2025,1,10)),
            new PerformResponse(2, LocalDate.of(2025,1,11)),
            new PerformResponse(3, LocalDate.of(2025,1,12))
        );
        return ResponseEntity.ok().body(ApiResponse.success(concertList));
    }
}
