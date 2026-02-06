package com.solinone.todoc.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProviderHomeResponse {
    private List<PlaceWithBoardResponse> places;

    public static ProviderHomeResponse of(List<PlaceWithBoardResponse> places) {
        return ProviderHomeResponse.builder()
                .places(places)
                .build();
    }
}
