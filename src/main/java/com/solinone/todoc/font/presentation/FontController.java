package com.solinone.todoc.font.presentation;

import com.solinone.todoc.font.application.FontEmotionAnalysisService;
import com.solinone.todoc.font.application.FontRecommendationService;
import com.solinone.todoc.font.application.FontService;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.dto.request.FontAutoRecommendRequest;
import com.solinone.todoc.font.dto.response.FontAutoRecommendResponse;
import com.solinone.todoc.font.dto.response.FontResponse;
import com.solinone.todoc.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fonts")
@RequiredArgsConstructor
@Tag(
        name = "폰트",
        description = "폰트 조회 및 추천"
)
public class FontController {
    private final FontService fontService;
    private final FontEmotionAnalysisService fontEmotionAnalysisService;
    private final FontRecommendationService fontRecommendationService;

    @GetMapping
    public ApiResponse<List<FontResponse>> getFonts(
            @RequestParam(required = false)FontCategory category
            ) {
        if (category == null) {
            return ApiResponse.success(fontService.getAllFonts());
        }
        return ApiResponse.success(fontService.getFontsByCategory(category));
    }

    @PostMapping("/recommend/auto")
    public ApiResponse<FontAutoRecommendResponse> autoRecommend (
            @Valid @RequestBody FontAutoRecommendRequest request
            ) {
        //1. AI 감정 분석(내부용)
        List<FontCategory> categories =
                fontEmotionAnalysisService.analyze(request.getContent());

        //2. 폰트 추천
        List<FontResponse> fonts =
                fontRecommendationService.recommend(categories)
                        .stream()
                        .map(FontResponse::from)
                        .toList();

        //3. UI 기준 응답
        return ApiResponse.success(
                FontAutoRecommendResponse.of(fonts)
        );
    }
}
