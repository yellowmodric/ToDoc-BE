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
    private List<String> themeUrls;

    public static ThemeResponse from(Theme theme) {
        List<String> tags = theme.getThemeTags().stream()
                .map(themeTag -> themeTag.getTag().getTagName())
                .collect(Collectors.toList());

        return ThemeResponse.builder()
                .themeId(theme.getThemeId())
                .themeName(theme.getThemeName())
                .themeUrls(generateThemeUrls(theme.getThemeId()))
                .tags(tags)
                .build();
    }

    private static List<String> generateThemeUrls(Long themeId) {
        String baseUrl = "https://todocbucket.s3.ap-northeast-2.amazonaws.com/theme/";

        if (themeId == 5) {
            return List.of(baseUrl + "5/1.png");
            
        }

        return List.of(
                baseUrl + themeId + "/1.png",
                baseUrl + themeId + "/2.png",
                baseUrl + themeId + "/3.png",
                baseUrl + themeId + "/4.png"
        );
    }
}
