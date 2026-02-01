package com.solinone.todoc.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessVerificationResponse {

    private boolean valid;
    private String message;
    private String businessNumber;

    public static BusinessVerificationResponse valid(String businessNumber) {
        return BusinessVerificationResponse.builder()
                .valid(true)
                .message("유효한 사업자등록번호입니다.")
                .businessNumber(businessNumber)
                .build();
    }

    public static BusinessVerificationResponse invalid(String businessNumber, String message) {
        return BusinessVerificationResponse.builder()
                .valid(false)
                .message(message)
                .businessNumber(businessNumber)
                .build();
    }
}
