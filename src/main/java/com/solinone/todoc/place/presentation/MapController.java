package com.solinone.todoc.place.presentation;

import com.solinone.todoc.content.application.MyContentService;
import com.solinone.todoc.content.dto.response.LatestContentResponse;
import com.solinone.todoc.content.dto.response.MyContentResponse;
import com.solinone.todoc.global.response.ApiResponse;
import com.solinone.todoc.global.security.CustomUserDetails;
import com.solinone.todoc.place.application.PlaceMapService;
import com.solinone.todoc.place.application.PlaceService;
import com.solinone.todoc.place.dto.response.PlaceMapResponse;
import com.solinone.todoc.place.dto.response.ShowUiType;
import com.solinone.todoc.place.exception.map.InvalidLocationException;
import com.solinone.todoc.place.exception.map.InvalidRadiusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map/places")
@RequiredArgsConstructor
@Tag(name = "지도", description = "지도 관련 API")
public class MapController {
    private final PlaceMapService placeMapService;
    private final MyContentService myContentService;

    @GetMapping
    @Operation(summary = "주변 가게 조회")
    public ApiResponse<List<PlaceMapResponse>> getNearbyPlaces(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "1000") int radius,
            @RequestParam ShowUiType ui
    ) {
        //1. 좌표 범위 검증
        if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
            throw new InvalidLocationException();
        }

        //2. (0,0) 방어
        if (lat == 0.0 && lng == 0.0) {
            throw new InvalidLocationException();
        }

        //3. radius 방어
        if (radius <= 0) {
            throw new InvalidRadiusException();
        }

        Long userId = (user != null) ? user.getUserId() : null;

        return ApiResponse.success(
                placeMapService.getNearbyPlaces(userId, lat, lng, radius, ui)
        );
    }

    @GetMapping("/{placeId}/contents/latest")
    @Operation(summary = "매장 상세에서 최신 방명록 조회")
    public ApiResponse<List<LatestContentResponse>> getLatestContents(
            @PathVariable Long placeId
    ) {
        return ApiResponse.success(
                placeMapService.getLatestContent(placeId)
        );
    }

    @GetMapping("/{placeId}/contents/me")
    @PreAuthorize("hasRole('VISITOR')")
    @Operation(summary = "매장 상세에서 내 방명록 조회")
    public ApiResponse<List<MyContentResponse>> getMyContents(
            @PathVariable Long placeId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.success(
                myContentService.getMyContents(placeId, user.getUserId())
        );
    }
}
