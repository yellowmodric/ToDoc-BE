package com.solinone.todoc.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/*
 * 삭제 예정 - Swagger 테스트용
 */
@Getter
@AllArgsConstructor
public class ExampleResponse {

    private String message;
    private Integer number;
    private LocalDateTime timestamp;
}
