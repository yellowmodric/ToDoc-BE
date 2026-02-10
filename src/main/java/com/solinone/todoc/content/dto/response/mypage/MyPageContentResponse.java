package com.solinone.todoc.content.dto.response.mypage;

import com.solinone.todoc.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class MyPageContentResponse {
    private Long contentId;
    private String content;
    private String theme_url;
    private Long fontId;
    private String createdAt;
    private Long placeId;
    private String placeName;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");

    public static MyPageContentResponse from(Content content) {
        return new MyPageContentResponse(
                content.getContentId(),
                content.getContent(),
                content.getThemeUrl(),
                content.getFont().getFontId(),
                content.getCreatedAt().format(FORMATTER),
                content.getBoard().getPlace().getPlaceId(),
                content.getBoard().getPlace().getPlaceName()
        );
    }
}
