package com.solinone.todoc.content.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solinone.todoc.content.domain.Content;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContentCreateResponse {

    private Long contentId;
    private String placeName;
    private Integer orderNumber;
    private String content;
    private String fontName;
    private String themeName;
    private String postColor;
    private String fontColor;

    @JsonFormat(pattern = "yy.MM.dd HH:mm")
    private LocalDateTime createdAt;

    public static ContentCreateResponse from(Content content, Integer orderNumber, String placeName) {
        return ContentCreateResponse.builder()
                .contentId(content.getContentId())
                .orderNumber(orderNumber)
                .content(content.getContent())
                .fontName(content.getFont().getFontName())
                .themeName(content.getBoard().getTheme().getThemeName())
                .postColor(content.getPostColor())
                .fontColor(content.getFontColor())
                .createdAt(content.getCreatedAt())
                .placeName(placeName)
                .build();
    }
}
