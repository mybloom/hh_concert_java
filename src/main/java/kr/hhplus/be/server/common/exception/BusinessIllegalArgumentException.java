package kr.hhplus.be.server.common.exception;

public class BusinessIllegalArgumentException extends BusinessException {

    public BusinessIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
