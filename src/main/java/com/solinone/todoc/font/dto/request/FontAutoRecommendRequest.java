package com.solinone.todoc.font.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FontAutoRecommendRequest {
    @NotBlank
    private String content;
    private Long boardId;
}
