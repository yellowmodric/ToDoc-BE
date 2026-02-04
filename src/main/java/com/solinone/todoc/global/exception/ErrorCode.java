package com.solinone.todoc.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //example (test)
    EXAMPLE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "test001", "테스트 오류 발생"),

    //board (B)
    BOARD_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "B001", "이미 방명록 판이 존재하는 가게입니다."),

    //common (C)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버에서 알 수 없는 오류가 발생했습니다"),
    INVALID_ARGUMENT_TYPE(HttpStatus.BAD_REQUEST, "C002", "요청 값 타입이 잘못되었습니다"),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "C003", "요청 값이 누락되었습니다"),
    INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "C004", "요청 값이 잘못되었습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근 권한이 없습니다"),
    //user (U)
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U001", "이미 사용 중인 이메일입니다"),
    INVALID_CREDENTIALS_EXCEPTION(HttpStatus.BAD_REQUEST, "U002", "사용자 인증에 실패했습니다."),

    //place (P)
    INVALID_BUSINESS_NUMBER(HttpStatus.INTERNAL_SERVER_ERROR, "P001", "사업자번호 인증과정중 오류가 발생했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "P002", "사용자를 찾을 수 없습니다."),
    DUPLICATE_PLACE(HttpStatus.BAD_REQUEST, "P003", "같은 사업자번호와 주소로 이미 등록된 가게가 있습니다."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "P004", "장소를 찾을 수 없습니다."),
    PLACE_USER_MISMATCH(HttpStatus.BAD_REQUEST, "P005", "가게와 사장님이 일치하지 않습니다."),

    //theme (T)
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "테마를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
