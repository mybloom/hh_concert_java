package kr.hhplus.be.server.common.exception;

import kr.hhplus.be.server.common.exception.errorcode.ErrorCode;

public class BusinessIllegalArgumentException extends BusinessException {

    public BusinessIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
