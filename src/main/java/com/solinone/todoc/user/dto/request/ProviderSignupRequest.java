package com.solinone.todoc.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solinone.todoc.place.domain.PlaceType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class ProviderSignupRequest {

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotBlank(message = "닉네임은 필수입니다")
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    private String password;

    @NotBlank(message = "사업자등록번호는 필수입니다")
    private String businessNumber;

    @NotNull(message = "가게유형은 필수입니다")
    private PlaceType placeType;

    @NotBlank
    private String placeName;

    @NotBlank
    private String address;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate openedAt;
}
