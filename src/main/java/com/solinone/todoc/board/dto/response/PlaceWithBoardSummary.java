package com.solinone.todoc.board.dto.response;


import com.solinone.todoc.place.domain.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceWithBoardSummary {
    private Long placeId;
    private String placeName;
    private boolean hasBoard;

    public static PlaceWithBoardSummary of(Place place, boolean hasBoard) {
        return PlaceWithBoardSummary.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .hasBoard(hasBoard)
                .build();
    }
}
