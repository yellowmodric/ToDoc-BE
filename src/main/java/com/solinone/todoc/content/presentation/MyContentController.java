package com.solinone.todoc.content.presentation;

import com.solinone.todoc.content.application.MyContentService;
import com.solinone.todoc.content.dto.response.MyPageContentResponse;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage/contents")
@RequiredArgsConstructor
@Tag(name = "마이페이지", description = "마이페이지 API")
public class MyContentController {
    private final MyContentService myContentService;

    @GetMapping
    @PreAuthorize("hasRole('VISITOR')")
    @Operation(summary = "마이페이지 방명록 리스트 조회")
    public ApiResponse<List<MyPageContentResponse>> getMyPageContents(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.success(myContentService.getMyPageContents(userDetails.getUserId()));
    }
}
