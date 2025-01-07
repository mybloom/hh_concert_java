package kr.hhplus.be.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum IllegalArgumentErrorCode implements ErrorCode {
    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액은 1 이상이어야 합니다."),
    INVALID_USE_AMOUNT(HttpStatus.BAD_REQUEST, "사용 금액은 1 이상이어야 합니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
