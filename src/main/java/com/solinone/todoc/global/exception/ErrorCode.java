package com.solinone.todoc.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //example (test)
    EXAMPLE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "test001", "테스트 오류 발생"),

    //common (C)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버에서 알 수 없는 오류가 발생했습니다"),
    INVALID_ARGUMENT_TYPE(HttpStatus.BAD_REQUEST, "C002", "요청 값 타입이 잘못되었습니다"),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "C003", "요청 값이 누락되었습니다"),
    INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "C004", "요청 값이 잘못되었습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근 권한이 없습니다");
    //user (U)

    //place (P)

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
