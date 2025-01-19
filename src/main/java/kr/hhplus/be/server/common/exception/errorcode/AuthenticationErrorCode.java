package kr.hhplus.be.server.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements ErrorCode {
    //TOKEN
    UNAUTHENTICATED_TOKEN(HttpStatus.UNAUTHORIZED, "대기열 토큰을 찾을 수 없습니다. 인증 후 다시 시도해 주세요");

    private final HttpStatus httpStatus;
    private final String message;
}
