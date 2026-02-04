package com.solinone.todoc.board.presentation;

import com.solinone.todoc.board.application.BoardService;
import com.solinone.todoc.board.application.ThemeService;
import com.solinone.todoc.board.dto.request.BoardCreateRequest;
import com.solinone.todoc.board.dto.response.ThemeResponse;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "방명록", description = "방명록관리 API")
public class BoardController {

    private final ThemeService themeService;
    private final BoardService boardService;

    @GetMapping("/themes")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "방명록 판 테마 조회")
    public ApiResponse<List<ThemeResponse>> getThemes() {
        List<ThemeResponse> themes = themeService.getAllThemes();
        return ApiResponse.success(themes);
    }

    @PostMapping("/boards")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "방명록 판 생성")
    public ApiResponse<Void> createBoard(@Valid @RequestBody BoardCreateRequest request,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardService.createBoard(request,userDetails.getUserId());
        return new ApiResponse<>("방명록 판이 생성되었습니다", null);
    }
}
