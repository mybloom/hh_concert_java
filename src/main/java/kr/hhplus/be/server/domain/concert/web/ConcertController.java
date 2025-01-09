package kr.hhplus.be.server.domain.concert.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.concert.domain.ConcertService;
import kr.hhplus.be.server.domain.concert.domain.PageProperties;
import kr.hhplus.be.server.domain.concert.domain.dto.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Response Concert", description = "Response Concert API")
@RequiredArgsConstructor
@RestController
public class ConcertController {

    private final ConcertService concertService;
    private final PageProperties pageProperties;

    @Operation(summary = "콘서트 예약 가능 날짜", description = "콘서트 예약 가능 날짜 조회 API")
    @GetMapping("/api/concerts/{concertId}/schedules")
    public ConcertResponse retrieveConcertSchedules(
        @PathVariable(name = "concertId") final long concertId,
        @RequestParam(name = "page") final Integer page
    ) {

        int limit = pageProperties.getSize();
        final Integer offset = (page - 1) * limit;

        ConcertResponse concertResponse = concertService.retrieveConcertSchedules(concertId, offset, limit);
        return concertResponse;
    }
}
