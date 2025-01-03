package kr.hhplus.be.server.domain.token.web;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.apiresponse.ApiResponse;
import kr.hhplus.be.server.domain.token.web.dto.TokenRequest;
import kr.hhplus.be.server.domain.token.web.dto.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Operation(summary = "토큰생성", description = "토큰생성 API")
    @PostMapping("/api/queue/token")
    public ResponseEntity<ApiResponse<TokenResponse>> createToken(
        @RequestBody TokenRequest request) {

        long userId = request.getUserId();

        if (userId == 0) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("Invalid userId"));
        }

        String queueToken = "1645635768393-4a6b3e1b-ea09-4c9d-9f1d-5bb07e49c4bc";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Queue-Token", queueToken);

        if (userId == 1) {
            return ResponseEntity.ok()
                .headers(headers)
                .body(ApiResponse.success(new TokenResponse("running", 0)));
        }

        return ResponseEntity.ok()
            .headers(headers)
            .body(ApiResponse.success(new TokenResponse("waiting", 3)));
    }

}
