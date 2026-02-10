package com.solinone.todoc.content.dto.request;

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

    @NotNull(message = "방명록 테마url은 필수입니다")
    private String themeUrl;

    @NotNull(message = "위도 값은 필수입니다")
    private Double userLatitude;

    @NotNull(message = "경도 값은 필수입니다")
    private Double userLongitude;
}
