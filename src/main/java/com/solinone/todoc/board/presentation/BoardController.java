package com.solinone.todoc.board.presentation;

import com.solinone.todoc.board.application.BoardService;
import com.solinone.todoc.board.application.ThemeService;
import com.solinone.todoc.board.dto.request.BoardCreateRequest;
import com.solinone.todoc.board.dto.response.BoardDetailResponse;
import com.solinone.todoc.board.dto.response.ProviderHomeResponse;
import com.solinone.todoc.board.dto.response.ThemeResponse;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.response.MessageResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import com.solinone.todoc.infrastructure.sse.SseEmitterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "방명록", description = "방명록관리 API")
public class BoardController {

    private final ThemeService themeService;
    private final BoardService boardService;
    private final SseEmitterService sseEmitterService;

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
    public ApiResponse<MessageResponse> createBoard(@Valid @RequestBody BoardCreateRequest request,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardService.createBoard(request,userDetails.getUserId());
        return ApiResponse.success(new MessageResponse("방명록 판이 생성되었습니다."));
    }

    @GetMapping("/provider/home")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "사장님 홈 - 가게 목록 + 첫번째 가게 상세")
    public ApiResponse<ProviderHomeResponse> getProviderHome(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ProviderHomeResponse response = boardService.getProviderHome(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @GetMapping(value = "/boards/{placeId}/stream", produces = "text/event-stream")
    @Operation(summary = "방명록 판 조회시 sse연결")
    public SseEmitter streamContent(@PathVariable("placeId") Long placeId) {
        return sseEmitterService.createEmitter(placeId);
    }

    @GetMapping("/boards/{placeId}")
    @Operation(summary = "QR스캔 후 사용자 접근 데이터")
    public ApiResponse<BoardDetailResponse> getBoardByPlaceId(@PathVariable("placeId") Long placeId) {
        BoardDetailResponse response = boardService.getBoardByPlaceId(placeId);
        return ApiResponse.success(response);
    }

    @GetMapping("/provider/places/{placeId}")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "사장님 본인 가게 상세 조회")
    public ApiResponse<ProviderHomeResponse> getMyPlaceBoard(@PathVariable("placeId") Long placeId,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ProviderHomeResponse response = boardService.getMyPlaceBoard(placeId, userDetails.getUserId());
        return ApiResponse.success(response);
    }
}
