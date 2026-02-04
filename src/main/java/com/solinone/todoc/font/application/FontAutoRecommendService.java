package com.solinone.todoc.font.application;

import com.solinone.todoc.font.application.ai.FontReasonPromptFactory;
import com.solinone.todoc.font.application.ai.FontWithCategory;
import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.dto.response.FontResponse;
import com.solinone.todoc.font.exception.FontReasonGenerateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FontAutoRecommendService {
    private final FontRecommendationService recommendationService;
    private final FontReasonGenerateService fontReasonGenerateService;

    public List<FontResponse> recommend(
            String input,
            List<FontCategory> categories
    ) {
        //1. 폰트 추천
        List<Font> fonts = recommendationService.recommend(categories);

        //2. AI 설명 입력용 DTO 변환
        List<FontWithCategory> fontWithCategories =
            fonts.stream()
                    .map(font -> new FontWithCategory(
                            font.getFontName(),
                            font.getCategory()
                    ))
                    .toList();

        //3. 추천 이유 생성
        Map<String, String> reasons;
        try {
            reasons = fontReasonGenerateService.generateReasons(
                    input, fontWithCategories
            );
        } catch (FontReasonGenerateException e) {
            //이유 생성 실패해도 추천은 유지
            reasons = Map.of();
        }

        //4. 최종 응답
        final Map<String, String> finalReasons = reasons;

        return fonts.stream()
                .map(font ->
                        FontResponse.from(
                                font,
                                finalReasons.get(font.getFontName())
                        ))
                .toList();
    }
}
