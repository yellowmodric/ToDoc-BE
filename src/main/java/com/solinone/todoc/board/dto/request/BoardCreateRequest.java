package com.solinone.todoc.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequest {

    @NotNull(message = "장소 ID는 필수입니다")
    private Long placeId;

    @NotNull(message = "테마 ID는 필수입니다")
    private Long themeId;

}
