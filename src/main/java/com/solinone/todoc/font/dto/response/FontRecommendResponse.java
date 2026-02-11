package com.solinone.todoc.font.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FontRecommendResponse {
    private String themeUrl;
    private List<FontResponse> fonts;
}
