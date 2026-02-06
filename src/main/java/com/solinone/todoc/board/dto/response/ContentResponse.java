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
    private Integer contentLength;
    private Long fontId;
    private String themeUrl;
    @JsonFormat(pattern = "yy.MM.dd HH:mm")
    private LocalDateTime createdAt;

    public static ContentResponse from(Content content) {
        return ContentResponse.builder()
                .contentId(content.getContentId())
                .content(content.getContent())
                .contentLength(content.getContent().length())
                .fontId(content.getFont().getFontId())
                .themeUrl(content.getThemeUrl())
                .createdAt(content.getCreatedAt())
                .build();
    }

}
