package kr.hhplus.be.server.common.successresponse;


public record SuccessResponse<T>(int code, T data) {

}


