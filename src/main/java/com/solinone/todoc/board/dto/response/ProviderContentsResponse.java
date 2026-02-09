package com.solinone.todoc.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProviderContentsResponse {

    private PlaceInfo currentPlace;
    private Object contents; // Page<ContentResponse or CursorResponse<ContentResponse>
    private List<PlaceWithBoardSummary> places;

    public static ProviderContentsResponse of(
            PlaceInfo currentPlace,
            Object contents,
            List<PlaceWithBoardSummary> places) {
        return ProviderContentsResponse.builder()
                .currentPlace(currentPlace)
                .contents(contents)
                .places(places)
                .build();
    }
}

