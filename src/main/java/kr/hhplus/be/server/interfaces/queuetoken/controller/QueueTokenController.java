package kr.hhplus.be.server.interfaces.queuetoken.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.queuetoken.application.result.QueueTokenResult;
import kr.hhplus.be.server.interfaces.queuetoken.dto.TokenRequest;
import kr.hhplus.be.server.interfaces.usecase.QueueTokenCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "대기열 토큰", description = "대기열 토큰 API")
@RequiredArgsConstructor
@RestController
public class QueueTokenController {

    private final QueueTokenCreateUseCase queueTokenCreateUseCase;

    @Operation(
        summary = "대기열 토큰 발급",
        description = "대기열에 진입하기 위해 대기열 토큰을 발급받는다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "큐 토큰이 성공적으로 생성됨",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QueueTokenResult.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/api/queue/tokens")
    public ResponseEntity<QueueTokenResult> createToken(@Valid @RequestBody TokenRequest request) {

        QueueTokenResult response = queueTokenCreateUseCase.create(request.getUserId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-queue-token", response.getTokenUuid());
        return ResponseEntity.ok().headers(headers).body(response);
    }

}
