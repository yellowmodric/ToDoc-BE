package com.solinone.todoc.place.infrastructure;

public interface PlaceMapProjection {
    Long getPlaceId();
    String getPlaceName();
    Double getLatitude();
    Double getLongitude();
    Long getContentCount();
    String getAddress();
}
