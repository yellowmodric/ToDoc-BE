package com.solinone.todoc.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solinone.todoc.content.domain.Content;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContentResponse {

    private Long contentId;
    private String content;
    private String postColor;
    private String fontColor;
    private String fontName;
    @JsonFormat(pattern = "yy.MM.dd HH:mm")
    private LocalDateTime createdAt;

    public static ContentResponse from(Content content) {
        return ContentResponse.builder()
                .contentId(content.getContentId())
                .content(content.getContent())
                .postColor(content.getPostColor())
                .fontColor(content.getFontColor())
                .fontName(content.getFont().getFontName())
                .createdAt(content.getCreatedAt())
                .build();
    }

}
