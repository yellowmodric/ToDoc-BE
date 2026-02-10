package com.solinone.todoc.content.dto.response;

import com.solinone.todoc.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class LatestContentResponse {
    private Long contentId;
    private String content;
    private String themeUrl;
    private Long fontId;
    private String createdAt;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");

    public static LatestContentResponse from(Content content) {
        return new LatestContentResponse(
                content.getContentId(),
                content.getContent(),
                content.getThemeUrl(),
                content.getFont().getFontId(),
                content.getCreatedAt().format(FORMATTER)
        );
    }
}
