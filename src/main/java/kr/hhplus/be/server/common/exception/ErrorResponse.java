package kr.hhplus.be.server.common.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus code, String message) {

}

