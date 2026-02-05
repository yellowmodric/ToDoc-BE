package com.solinone.todoc.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentCreateRequest {

    @NotNull(message = "폰트 ID는 필수입니다")
    private Long fontId;

    @NotNull(message = "방명록 내용은 필수입니다")
    private String content;

    @NotBlank(message = "포스트잇 색상은 필수입니다")
    private String postColor;

    @NotBlank(message = "폰트 색상은 필수입니다")
    private String fontColor;
}
