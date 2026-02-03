package com.solinone.todoc.board.dto.response;

import com.solinone.todoc.board.domain.Theme;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ThemeResponse {

    private Long themeId;
    private String themeName;
    private List<String> tags;

    public static ThemeResponse from(Theme theme) {
        List<String> tags = theme.getThemeTags().stream()
                .map(themeTag -> themeTag.getTag().getTagName())
                .collect(Collectors.toList());

        return ThemeResponse.builder()
                .themeId(theme.getThemeId())
                .themeName(theme.getThemeName())
                .tags(tags)
                .build();
    }
}
