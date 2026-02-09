package com.solinone.todoc.place.dto.response;

import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.domain.PlaceType;
import com.solinone.todoc.place.infrastructure.PlaceMapProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class PlaceMapResponse {
    private Long placeId;
    private String placeName;
    private double latitude;
    private double longitude;
    private long contentCount;
    private String address;
    private PlaceType placeType;

    //방명록 정보
    private MyContentStatus myStatus;
    private String myContent;
    private LocalDateTime lastVisitedAt;

    public PlaceMapResponse(
            Long placeId,
            String placeName,
            double latitude,
            double longitude,
            long contentCount,
            String address,
            PlaceType placeType
    ) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contentCount = contentCount;
        this.address = address;
        this.placeType = placeType;
    }

    public static PlaceMapResponse from(PlaceMapProjection place) {
        return new PlaceMapResponse(
                place.getPlaceId(),
                place.getPlaceName(),
                place.getLatitude(),
                place.getLongitude(),
                place.getContentCount(),
                place.getAddress(),
                place.getPlaceType()
        );
    }
}
