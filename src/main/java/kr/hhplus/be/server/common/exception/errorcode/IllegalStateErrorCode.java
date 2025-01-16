package kr.hhplus.be.server.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum IllegalStateErrorCode implements ErrorCode {
    //TOKEN
    UNVERIFIED_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 토큰이 아닙니다."),
    UNVERIFIED_TOKEN_STATUS(HttpStatus.BAD_REQUEST, "토큰 상태를 요청하신 상태로 변경할 수 없습니다.");



    private final HttpStatus httpStatus;
    private final String message;

}
