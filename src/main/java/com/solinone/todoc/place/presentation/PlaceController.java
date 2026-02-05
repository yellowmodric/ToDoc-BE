package com.solinone.todoc.place.presentation;

import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.response.MessageResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import com.solinone.todoc.place.application.PlaceService;
import com.solinone.todoc.place.dto.request.PlaceCreateRequest;
import com.solinone.todoc.place.dto.response.PlaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
@Tag(name = "가게", description = "가게관리 API")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('PROVIDER')")
    @Operation(summary = "가게 추가 등록")
    public ApiResponse<MessageResponse> register(@Valid @RequestBody PlaceCreateRequest request, @AuthenticationPrincipal CustomUserDetails user) {
        placeService.createPlaceByOwner(request, user.getUserId());
        return ApiResponse.success(new MessageResponse("가게 등록이 완료되었습니다."));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PROVIDER')")
    @Operation(summary = "내가 등록한 가게 목록 조회")
    public ApiResponse<List<PlaceResponse>> getMyPlaces(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.success(
                placeService.getMyPlaces(user.getUserId())
        );
    }
}
