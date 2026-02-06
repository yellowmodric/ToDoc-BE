package com.solinone.todoc.place.dto.response;

import com.solinone.todoc.place.domain.Place;
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

    //방명록 정보
    private MyContentStatus myStatus;
    private String myContent;
    private LocalDateTime lastVistedAt;

    public PlaceMapResponse(
            Long placeId,
            String placeName,
            double latitude,
            double longitude,
            long contentCount
    ) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contentCount = contentCount;
    }

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
