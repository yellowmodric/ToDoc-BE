package com.solinone.todoc.font.dto.response;

import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.domain.FontField;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FontResponse {
    private Long fontId;
    private String fontName;
    private String fontNameEng;
    private FontCategory category;
    private FontField field;
    private String reason;

    public static FontResponse from(Font font) {
        return FontResponse.builder()
                .fontId(font.getFontId())
                .fontName(font.getFontName())
                .fontNameEng(font.getFontNameEng())
                .category(font.getCategory())
                .field(font.getField())
                .build();
    }

    public static FontResponse from(Font font, String reason) {
        return FontResponse.builder()
                .fontId(font.getFontId())
                .fontName(font.getFontName())
                .fontNameEng(font.getFontNameEng())
                .category(font.getCategory())
                .field(font.getField())
                .reason(reason)
                .build();
    }
}
