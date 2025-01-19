package kr.hhplus.be.server.common.exception;

import kr.hhplus.be.server.common.exception.errorcode.ErrorCode;

public class BusinessIllegalStateException extends BusinessException {

    public BusinessIllegalStateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
