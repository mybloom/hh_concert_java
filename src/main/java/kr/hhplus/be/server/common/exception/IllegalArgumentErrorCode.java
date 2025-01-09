package kr.hhplus.be.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum IllegalArgumentErrorCode implements ErrorCode {
    //POINT
    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액은 1 이상이어야 합니다."),
    INVALID_USE_AMOUNT(HttpStatus.BAD_REQUEST, "사용 금액은 1 이상이어야 합니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),

    //USER
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),

    //TOKEN
    QUEUE_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 대기열 토큰을 찾을 수 없습니다.");



    private final HttpStatus httpStatus;
    private final String message;

}
