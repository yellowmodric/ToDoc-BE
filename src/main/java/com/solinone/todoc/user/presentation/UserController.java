package com.solinone.todoc.user.presentation;

import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.response.MessageResponse;
import com.solinone.todoc.infrastructure.business.BusinessRegistrationClient;
import com.solinone.todoc.user.application.UserService;
import com.solinone.todoc.user.dto.request.*;
import com.solinone.todoc.user.dto.response.BusinessVerificationResponse;
import com.solinone.todoc.user.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public ApiResponse<MessageResponse> checkEmail(@Valid @RequestBody EmailCheckRequest request) {
        userService.validateDuplicateEmail(request.getEmail());
        return ApiResponse.success(new MessageResponse("사용 가능 이메일입니다."));
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
    public ApiResponse<MessageResponse> signupVisitor(@Valid @RequestBody VisitorSignupRequest request) {
        userService.signUpVisitor(request);
        return ApiResponse.success(new MessageResponse("회원가입이 완료되었습니다."));
    }

    @PostMapping("/signup/provider")
    @Operation(summary = "사장님 회원가입")
    public ApiResponse<MessageResponse> signupProvider(@Valid @RequestBody ProviderSignupRequest request) {
        userService.signUpProvider(request);
        return ApiResponse.success(new MessageResponse("회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ApiResponse.success(response);
    }
}
