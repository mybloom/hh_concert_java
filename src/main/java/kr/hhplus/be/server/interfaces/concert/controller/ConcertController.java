package kr.hhplus.be.server.interfaces.concert.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.concert.domain.ConcertService;
import kr.hhplus.be.server.domain.concert.domain.PageProperties;
import kr.hhplus.be.server.domain.concert.domain.dto.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "콘서트", description = "콘서트 API")
@RequiredArgsConstructor
@RestController
public class ConcertController {

    private final ConcertService concertService;
    private final PageProperties pageProperties;

    @Operation(summary = "콘서트 예약 가능 날짜", description = "콘서트 예약 가능 날짜 조회 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "콘서트 스케줄 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConcertResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "콘서트를 찾을 수 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
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
