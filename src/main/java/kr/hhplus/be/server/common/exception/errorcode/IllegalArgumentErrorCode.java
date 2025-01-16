package kr.hhplus.be.server.common.exception.errorcode;

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
    INVALID_USER(HttpStatus.BAD_REQUEST, "사용자id는 필수입니다."),

    //TOKEN
    QUEUE_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 대기열 토큰을 찾을 수 없습니다."),

    //QueueOffset
    QUEUE_OFFSET_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 대기열 오프셋을 찾을 수 없습니다."),

    //CONCERT
    INVALID_CONCERT_ID(HttpStatus.BAD_REQUEST, "콘서트 ID를 찾을 수 없습니다."),

    //SEAT
    INVALID_SEAT_ID(HttpStatus.BAD_REQUEST, "좌석 ID를 찾을 수 없습니다."),
    SEAT_ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다."),

    //RESERVATION
    INVALID_RESERVATION_ID(HttpStatus.BAD_REQUEST, "예약 ID를 찾을 수 없습니다."),
    ALREADY_PAID_RESERVATION(HttpStatus.BAD_REQUEST, "이미 결제된 예약 정보입니다."),

    //PAYMENT
    INVALID_PAYMENT_ID(HttpStatus.BAD_REQUEST, "결제 ID를 찾을 수 없습니다."),

    //예약시간 만료에러
    INVALID_RESERVATION_TIMEOUT(HttpStatus.BAD_REQUEST, "예약 시간이 만료되었습니다.");



    private final HttpStatus httpStatus;
    private final String message;

}
