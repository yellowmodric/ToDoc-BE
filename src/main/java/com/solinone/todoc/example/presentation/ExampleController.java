package com.solinone.todoc.example.presentation;

import com.solinone.todoc.example.dto.response.ExampleResponse;
import com.solinone.todoc.example.exception.ExampleErrorException;
import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


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

    @GetMapping("/auth/userinfo")
    @Operation(summary = "jwt토큰에서 사용자 정보 추출")
    public ApiResponse<Map<String, Object>> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // userDetails값은 되도록 컨트롤러단에서 처리
        Map<String, Object> data = new HashMap<>();
        data.put("userId",  userDetails.getUserId()); //userId
        data.put("email", userDetails.getUsername()); //이메일
        data.put("nickname", userDetails.getNickname()); //닉네임
        data.put("role", userDetails.getUser().getRole()); //VISITOR or PROVIDER
        data.put("name", userDetails.getName()); //이름
        return ApiResponse.success(data);
    }

    @GetMapping("/auth/all-users")
    @PreAuthorize("hasAnyRole('VISITOR', 'PROVIDER')")
    @Operation(summary = "회원가입한 유저만 가능")
    public ApiResponse<Void> allUsers() {
        return ApiResponse.success(null);
    }
}
