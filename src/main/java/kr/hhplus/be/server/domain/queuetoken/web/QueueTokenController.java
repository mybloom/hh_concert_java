package kr.hhplus.be.server.domain.queuetoken.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.hhplus.be.server.common.apiresponse.BusinessApiResponse;
import kr.hhplus.be.server.domain.queuetoken.web.dto.TokenRequest;
import kr.hhplus.be.server.domain.queuetoken.web.dto.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueTokenController {

    /*
        @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "대기열 토큰이 성공적으로 생성되었습니다.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = "{ \"id\": 1, \"userId\": 1, \"token\": \"e20d7b49-bf11-41ed-9662-27229eaa91a7\", \"tokenStatus\": \"Waiting\", \"createdAt\": \"2025-01-03T12:00:00\" }"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = "{ \"message\": \"사용자 id를 입력해주세요.\" }")
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "서버 오류 발생",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = "{ \"message\": \"Internal server error\" }")
                )
            )
        })*/
    @Operation(
        summary = "대기열 토큰 발급",
        description = "대기열에 진입하기 위해 대기열 토큰을 발급받는다."
    )
    @ApiResponses(value = {
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
    })
    @PostMapping("/api/queue/token")
    public ResponseEntity<BusinessApiResponse<TokenResponse>> createToken(
        @RequestBody TokenRequest request) {

        long userId = request.getUserId();

        if (userId == 0) {
            return ResponseEntity
                .badRequest()
                .body(BusinessApiResponse.failure(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid userId"));
        }

        String queueToken = "1645635768393-4a6b3e1b-ea09-4c9d-9f1d-5bb07e49c4bc";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Queue-Token", queueToken);

        if (userId == 1) {
            return ResponseEntity.ok()
                .headers(headers)
                .body(BusinessApiResponse.success(new TokenResponse("running", 0)));
        }

        return ResponseEntity.ok()
            .headers(headers)
            .body(BusinessApiResponse.success(new TokenResponse("waiting", 3)));
    }

}
