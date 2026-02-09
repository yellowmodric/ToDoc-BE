package com.solinone.todoc.board.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceInfo {
    private Long placeId;
    private String placeName;

    public static PlaceInfo of(Long placeId, String placeName) {
        return PlaceInfo.builder()
                .placeId(placeId)
                .placeName(placeName)
                .build();
    }
}
