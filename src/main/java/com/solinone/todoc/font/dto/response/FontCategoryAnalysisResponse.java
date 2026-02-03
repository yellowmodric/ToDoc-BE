package com.solinone.todoc.font.dto.response;

import com.solinone.todoc.font.domain.FontCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FontCategoryAnalysisResponse {
    private List<FontCategory> categories;
}
