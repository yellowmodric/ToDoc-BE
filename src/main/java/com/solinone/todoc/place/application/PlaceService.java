package com.solinone.todoc.place.application;

import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.dto.PlaceCreateRequest;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import com.solinone.todoc.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;


    public void createPlace(User owner, PlaceCreateRequest request) {
        log.info("가게 등록 시작 - 사용자: {}, 가게명: {}", owner.getUserId(), request.getPlaceName());

        Place place = Place.builder()
                .user(owner)
                .placeName(request.getPlaceName())
                .placeType(request.getPlaceType())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .businessNumber(request.getBusinessNumber())
                .openedAt(request.getOpenedAt())
                .build();
        placeRepository.save(place);
    }
}
