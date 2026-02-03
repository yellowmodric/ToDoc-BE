package com.solinone.todoc.font.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FontAutoRecommendResponse {
    private List<FontResponse> fonts;

    public static FontAutoRecommendResponse of(List<FontResponse> fonts) {
        return new FontAutoRecommendResponse(fonts);
    }
}
