package com.solinone.todoc.board.presentation;

import com.solinone.todoc.board.application.ThemeService;
import com.solinone.todoc.board.dto.response.ThemeResponse;
import com.solinone.todoc.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Tag(name = "방명록", description = "방명록관리 API")
public class BoardController {

    private final ThemeService themeService;

    @GetMapping("/themes")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "방명록 판 테마 조회")
    public ApiResponse<List<ThemeResponse>> getThemes() {
        List<ThemeResponse> themes = themeService.getAllThemes();
        return ApiResponse.success(themes);
    }

}
