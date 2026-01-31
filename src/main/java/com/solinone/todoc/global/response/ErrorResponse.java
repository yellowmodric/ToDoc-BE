package com.solinone.todoc.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
