package com.solinone.todoc.example.presentation;

import com.solinone.todoc.example.dto.response.ExampleResponse;
import com.solinone.todoc.example.exception.ExampleErrorException;
import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;
import com.solinone.todoc.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/*
 * 예시 컨트롤러
 *
 * 용도 :
 * 1. ApiResponse
 * 2. ErrorResponse
 * 3. Swagger
 */

@RestController
@RequestMapping("/api/examples")
@Tag(
        name = "예시",
        description = "테스트용 API"
)
public class ExampleController {

    @GetMapping("/success")
    public ApiResponse<ExampleResponse> testSuccess() {

        ExampleResponse data = new ExampleResponse(
                "성공",
                100,
                LocalDateTime.now()
        );
        return ApiResponse.success(data);
    }

    @GetMapping("/error")
    public ApiResponse<Void> testError() {
        throw new ExampleErrorException();
    }
}
