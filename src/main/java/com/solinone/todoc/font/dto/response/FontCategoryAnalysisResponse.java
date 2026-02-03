package com.solinone.todoc.font.dto.response;

import com.solinone.todoc.font.domain.FontCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
//JSON -> 객체 파싱용 DTO
public class FontCategoryAnalysisResponse {
    private List<FontCategory> categories;
}
