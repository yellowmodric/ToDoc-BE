package com.solinone.todoc.place.presentation;

import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import com.solinone.todoc.place.application.PlaceService;
import com.solinone.todoc.place.dto.request.PlaceCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
@Tag(name = "가게", description = "가게관리 API")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('PROVIDER')")
    @Operation(summary = "가게 추가 등록")
    public ApiResponse<Void> register(@Valid @RequestBody PlaceCreateRequest request, @AuthenticationPrincipal CustomUserDetails user) {
        placeService.createPlaceByOwner(request, user.getUserId());
        return ApiResponse.success(null);
    }
}
