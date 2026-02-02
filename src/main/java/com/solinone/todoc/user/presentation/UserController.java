package com.solinone.todoc.user.presentation;

import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.infrastructure.business.BusinessRegistrationClient;
import com.solinone.todoc.user.application.UserService;
import com.solinone.todoc.user.dto.request.*;
import com.solinone.todoc.user.dto.response.BusinessVerificationResponse;
import com.solinone.todoc.user.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원관리 API")
public class UserController {

    private final UserService userService;
    private final BusinessRegistrationClient businessClient;

    @PostMapping("/email/check")
    @Operation(summary = "이메일 중복체크")
    public ApiResponse<Void> checkEmail(@Valid @RequestBody EmailCheckRequest request) {
        userService.validateDuplicateEmail(request.getEmail());
        return new ApiResponse<>("사용 가능한 이메일입니다.", null);
    }

    @PostMapping("/business/verify")
    @Operation(summary = "사업자등록번호 인증")
    public ApiResponse<BusinessVerificationResponse> verifyBusiness(
            @Valid @RequestBody BusinessVerificationRequest request) {
        BusinessVerificationResponse response = businessClient.verifyBusinessNumber(request.getBusinessNumber());
        return ApiResponse.success(response);
    }

    @PostMapping("/signup/visitor")
    @Operation(summary = "사용자 회원가입")
    public ApiResponse<Void> signupVisitor(@Valid @RequestBody VisitorSignupRequest request) {
        userService.signUpVisitor(request);
        return new ApiResponse<>("회원가입이 완료되었습니다.", null);
    }

    @PostMapping("/signup/provider")
    @Operation(
            summary = "사장님 회원가입",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "사장님 회원가입 예시",
                                    value = """
                {
                  "name": "string",
                  "nickname": "string",
                  "email": "user@example.com",
                  "password": "stringst",
                  "businessNumber": "string",
                  "placeName": "string",
                  "address": "string",
                  "latitude": 0.1,
                  "longitude": 0.1,
                  "openedAt": "2026.02.02"
                }
                """
                            )
                    )
            )
    )
    public ApiResponse<Void> signupProvider(@Valid @RequestBody ProviderSignupRequest request) {
        userService.signUpProvider(request);
        return new ApiResponse<>("회원가입이 완료되었습니다.", null);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ApiResponse.success(response);
    }
}
