package com.solinone.todoc.content.presentation;

import com.solinone.todoc.content.application.ContentService;
import com.solinone.todoc.content.application.MyContentService;
import com.solinone.todoc.content.dto.request.ContentCreateRequest;
import com.solinone.todoc.content.dto.request.ContentDeleteRequest;
import com.solinone.todoc.content.dto.response.ContentCreateResponse;
import com.solinone.todoc.content.dto.response.MyPageContentResponse;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.response.MessageResponse;
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
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "방명록 작성", description = "방명록 작성 API")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/boards/{placeId}/contents")
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

    @DeleteMapping("/contents")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "가게 사장님 방명록 삭제")
    public ApiResponse<MessageResponse> deleteContents(@Valid @RequestBody ContentDeleteRequest request,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        contentService.deleteContents(request.getContentIds(), userDetails.getUserId());

        return ApiResponse.success(new MessageResponse("방명록 삭제 완료"));
    }
}
