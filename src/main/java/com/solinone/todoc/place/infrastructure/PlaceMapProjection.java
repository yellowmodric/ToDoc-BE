package com.solinone.todoc.place.infrastructure;

import com.solinone.todoc.place.domain.PlaceType;

public interface PlaceMapProjection {
    Long getPlaceId();
    String getPlaceName();
    Double getLatitude();
    Double getLongitude();
    Long getContentCount();
    String getAddress();
    PlaceType getPlaceType();
}
