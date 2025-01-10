package kr.hhplus.be.server.domain.queuetoken.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenService;
import kr.hhplus.be.server.domain.queuetoken.domain.dto.QueueTokenResponse;
import kr.hhplus.be.server.domain.queuetoken.web.dto.TokenRequest;
import kr.hhplus.be.server.domain.user.domain.User;
import kr.hhplus.be.server.domain.user.domain.UserService;
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

    private final QueueTokenService queueTokenService;
    private final UserService userService;

    @Operation(
        summary = "대기열 토큰 발급",
        description = "대기열에 진입하기 위해 대기열 토큰을 발급받는다."
    )
    /*@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적인 토큰 생성",
            content = @Content(schema = @Schema(implementation = BusinessApiResponse.class),
                examples = @ExampleObject(value = """
                        {
                            "code": "200",
                            "message": "성공",
                            "data": {
                                "queueStatus": "대기 중",
                                "waitPosition": 1
                            }
                        }
                    """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(schema = @Schema(implementation = BusinessApiResponse.class),
                examples = @ExampleObject(value = """
                        {
                            "code": "400",
                            "message": "잘못된 요청",
                            "data": null
                        }
                    """))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = BusinessApiResponse.class),
                examples = @ExampleObject(value = """
                        {
                            "code": "500",
                            "message": "서버 내부 오류",
                            "data": null
                        }
                    """)))
    })*/
    @PostMapping("/api/queue/tokens")
    public ResponseEntity<QueueTokenResponse> createToken(
        @RequestBody TokenRequest request) {
        User user = userService.retrieveUser(request.getUserId());

        QueueTokenResponse response = queueTokenService.create(user);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-queue-token", response.getTokenUuid());
        return ResponseEntity.ok().headers(headers).body(response);
    }

}
