package com.solinone.todoc.content.presentation;

import com.solinone.todoc.content.application.ContentService;
import com.solinone.todoc.content.dto.request.ContentCreateRequest;
import com.solinone.todoc.content.dto.response.ContentCreateResponse;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Tag(name = "방명록 작성", description = "방명록 작성 API")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/{placeId}/contents")
    @Operation(summary = "방명록 작성")
    public ApiResponse<ContentCreateResponse> createContent(
            @PathVariable("placeId") Long placeId,
            @Valid @RequestBody ContentCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ?  userDetails.getUserId() : null;
        ContentCreateResponse response = contentService.createContent(placeId, request, userId);

        return ApiResponse.success(response);
    }
}
