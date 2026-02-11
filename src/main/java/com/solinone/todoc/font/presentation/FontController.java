package com.solinone.todoc.font.presentation;

import com.solinone.todoc.font.application.FontAutoRecommendService;
import com.solinone.todoc.font.application.FontEmotionAnalysisService;
import com.solinone.todoc.font.application.FontRecommendationService;
import com.solinone.todoc.font.application.FontService;
import com.solinone.todoc.font.domain.AnalysisType;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.dto.request.FontAutoRecommendRequest;
import com.solinone.todoc.font.dto.response.FontAutoRecommendResponse;
import com.solinone.todoc.font.dto.response.FontRecommendResponse;
import com.solinone.todoc.font.dto.response.FontResponse;
import com.solinone.todoc.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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
    private final FontAutoRecommendService fontAutoRecommendService;

    @GetMapping
    @Operation(summary = "카테고리별 폰트 조회")
    public ApiResponse<List<FontResponse>> getFonts(
            @RequestParam(required = false)FontCategory category
            ) {
        if (category == null) {
            return ApiResponse.success(fontService.getAllFonts());
        }
        return ApiResponse.success(fontService.getFontsByCategory(category));
    }

    @PostMapping("/recommend/auto")
    @Operation(summary = "방명록 내용 기반 AI 폰트 추천")
    public ApiResponse<FontRecommendResponse> autoRecommend (
            @Valid @RequestBody FontAutoRecommendRequest request
            ) {
        //1. AI 감정 분석(방명록 내용 기반)
        List<FontCategory> categories =
                fontEmotionAnalysisService.analyze(request.getContent(), AnalysisType.GUESTBOOK);

        //2. 폰트 추천 + 테마 미리보기
        FontRecommendResponse response =
                fontAutoRecommendService.recommendWithTheme(
                        request.getBoardId(),
                        request.getContent(),
                        categories
                );

        //3. UI 기준 응답
        return ApiResponse.success(response);
    }

    @GetMapping("/recommend/search")
    @Operation(summary = "검색어 기반 AI 폰트 추천")
    public ApiResponse<FontAutoRecommendResponse> searchRecommend (
            @RequestParam String query
    ) {
        //1. AI 분석(검색어 기반)
        List<FontCategory> categories =
                fontEmotionAnalysisService.analyze(query, AnalysisType.SEARCH);

        //2. 폰트 추천
        List<FontResponse> fonts =
                fontAutoRecommendService.recommend(
                        query,
                        categories
                );

        //3. 응답
        return ApiResponse.success(
                FontAutoRecommendResponse.of(fonts)
        );
    }
}
