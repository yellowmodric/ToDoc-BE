package com.solinone.todoc.font.presentation;

import com.solinone.todoc.font.application.FontService;
import com.solinone.todoc.font.dto.response.FontResponse;
import com.solinone.todoc.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(
        name = "폰트",
        description = "폰트 조회 및 추천"
)
public class FontController {
    private final FontService fontService;

    @GetMapping("/fonts")
    public ApiResponse<List<FontResponse>> getAllFonts() {
        return ApiResponse.success(fontService.getAllFonts());
    }
}
