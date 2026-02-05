package com.solinone.todoc.place.application;

import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.dto.request.PlaceCreateRequest;
import com.solinone.todoc.place.dto.response.PlaceResponse;
import com.solinone.todoc.place.exception.DuplicatePlaceException;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import com.solinone.todoc.user.domain.User;
import com.solinone.todoc.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;


    public void createPlaceOnSignup(User owner, PlaceCreateRequest request) {
        validateDuplicatePlace(request.getBusinessNumber(), request.getAddress());

        Place place = buildPlace(owner, request);
        placeRepository.save(place);
    }


    public void createPlaceByOwner(PlaceCreateRequest request, Long userId) {
        User owner = userRepository.getReferenceById(userId);

        validateDuplicatePlace(request.getBusinessNumber(), request.getAddress());

        Place place = buildPlace(owner, request);
        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> getMyPlaces(Long userId) {
        return placeRepository.findByUser_UserIdOrderByCreatedAtDesc(userId).stream()
                .map(place -> new PlaceResponse(
                        place.getPlaceId(),
                        place.getPlaceName()
                ))
                .toList();
    }

    private void validateDuplicatePlace(String businessNumber, String address) {
        if (placeRepository.existsByBusinessNumberAndAddress(businessNumber, address)) {
            log.warn("중복 가게 등록 - businessNumber: {}, address: {}", businessNumber, address);
            throw new DuplicatePlaceException();
        }
    }

    private Place buildPlace(User owner, PlaceCreateRequest request) {
        return Place.builder()
                .user(owner)
                .placeName(request.getPlaceName())
                .placeType(request.getPlaceType())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .zoneCode(request.getZoneCode())
                .businessNumber(request.getBusinessNumber())
                .openedAt(request.getOpenedAt())
                .build();
    }
}
