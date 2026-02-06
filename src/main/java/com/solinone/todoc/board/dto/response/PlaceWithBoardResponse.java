package com.solinone.todoc.board.dto.response;

import com.solinone.todoc.board.domain.Board;
import com.solinone.todoc.place.domain.Place;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceWithBoardResponse {
    private Long placeId;
    private String placeName;
    private boolean hasBoard;
    private BoardDetailResponse board;

    public static PlaceWithBoardResponse of(Place place, Board board,
                                            List<ContentResponse> contents) {
        return PlaceWithBoardResponse.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .hasBoard(board != null)
                .board(board != null ? BoardDetailResponse.of(board, contents) : null)
                .build();
    }

    public static PlaceWithBoardResponse ofWithoutBoard(Place place) {
        return PlaceWithBoardResponse.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .hasBoard(false)
                .board(null)
                .build();
    }

    public static PlaceWithBoardResponse ofBasicInfo(Place place, boolean hasBoard) {
        return PlaceWithBoardResponse.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .hasBoard(hasBoard)
                .board(null)
                .build();
    }
}
