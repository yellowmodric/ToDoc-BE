package com.solinone.todoc.place.dto.response;

import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.infrastructure.PlaceMapProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceMapResponse {
    private Long placeId;
    private String placeName;
    private double latitude;
    private double longitude;
    private long contentCount;

    public static PlaceMapResponse from(PlaceMapProjection place) {
        return new PlaceMapResponse(
                place.getPlaceId(),
                place.getPlaceName(),
                place.getLatitude(),
                place.getLongitude(),
                place.getContentCount()
        );
    }
}
