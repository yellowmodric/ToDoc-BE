package com.solinone.todoc.content.dto.response;

import com.solinone.todoc.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyContentResponse {
    private Long contentId;
    private String content;
    private String themeUrl;
    private Long fontId;
    private LocalDateTime createdAt;

    public static MyContentResponse from(Content content) {
        return new MyContentResponse(
                content.getContentId(),
                content.getContent(),
                content.getThemeUrl(),
                content.getFont().getFontId(),
                content.getCreatedAt()
        );
    }
}
