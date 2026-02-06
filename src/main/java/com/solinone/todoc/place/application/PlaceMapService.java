package com.solinone.todoc.place.application;

import com.solinone.todoc.place.dto.response.PlaceMapResponse;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceMapService {
    private final PlaceRepository placeRepository;

    public List<PlaceMapResponse> getNearbyPlaces(
            double lat, double lng, int radius
    ) {
        return placeRepository.findNearbyPlaces(lat, lng, radius)
                .stream()
                .map(PlaceMapResponse::from)
                .toList();
    }
}
