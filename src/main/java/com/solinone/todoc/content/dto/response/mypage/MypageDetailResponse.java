package com.solinone.todoc.content.dto.response.mypage;

import com.solinone.todoc.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class MypageDetailResponse {
    private Long contentId;
    private Long placeId;
    private String placeName;
    private String content;
    private String createdAt;
    private FontDetail font;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");

    @Getter
    @AllArgsConstructor
    public static class FontDetail {
        private Long fontId;
        private String fontName;
        private String field;
        private String category;
    }

    public static MypageDetailResponse from(Content content) {
        return new MypageDetailResponse(
                content.getContentId(),
                content.getBoard().getPlace().getPlaceId(),
                content.getBoard().getPlace().getPlaceName(),
                content.getContent(),
                content.getCreatedAt().format(FORMATTER),
                new FontDetail(
                        content.getFont().getFontId(),
                        content.getFont().getFontName(),
                        content.getFont().getField().name(),
                        content.getFont().getCategory().name()
                )
        );
    }
}
