package com.solinone.todoc.content.dto.response.mypage;

import com.solinone.todoc.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyPageContentResponse {
    private Long contentId;
    private String content;
    private String theme_url;
    private Long fontId;
    private LocalDateTime createdAt;
    private Long placeId;
    private String placeName;

    public static MyPageContentResponse from(Content content) {
        return new MyPageContentResponse(
                content.getContentId(),
                content.getContent(),
                content.getThemeUrl(),
                content.getFont().getFontId(),
                content.getCreatedAt(),
                content.getBoard().getPlace().getPlaceId(),
                content.getBoard().getPlace().getPlaceName()
        );
    }
}
