package com.solinone.todoc.place.application;

import com.solinone.todoc.content.dto.response.LatestContentResponse;
import com.solinone.todoc.content.dto.response.MyLatestContentResponse;
import com.solinone.todoc.content.infrastructure.ContentRepository;
import com.solinone.todoc.place.dto.response.MyContentStatus;
import com.solinone.todoc.place.dto.response.PlaceMapResponse;
import com.solinone.todoc.place.dto.response.ShowUiType;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import com.solinone.todoc.place.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceMapService {
    private final PlaceRepository placeRepository;
    private final ContentRepository contentRepository;

    public List<PlaceMapResponse> getNearbyPlaces(
            Long userId, double lat, double lng, int radius, ShowUiType ui
    ) {
        List<PlaceMapResponse> places =
                placeRepository.findNearbyPlaces(lat, lng, radius)
                .stream()
                .map(PlaceMapResponse::from)
                .toList();

        //비로그인
        if (userId == null) {
            return places;
        }

        //1. placeId 목록 추출
        List<Long> placeIds =
                places.stream()
                        .map(PlaceMapResponse::getPlaceId)
                        .toList();

        //2. content에서 내 최신 방명록 조회
        Map<Long, MyLatestContentResponse> contentMap =
                contentRepository.findLatestUserAndPlace(userId, placeIds)
                        .stream()
                        .collect(Collectors.toMap(
                                MyLatestContentResponse::placeId,
                                dto -> dto
                        ));

        //3. 가장 가까운 매장 1개 계산
        PlaceMapResponse nearest = null;

        if (ui == ShowUiType.MAP) {
            nearest = places.stream()
                    .min(Comparator.comparing(p ->
                            DistanceUtil.distance(
                                    lat, lng,
                                    p.getLatitude(), p.getLongitude())
                    ))
                    .orElse(null);
        }

        //4. 상태 세팅
        for (PlaceMapResponse place : places) {
            MyLatestContentResponse content = contentMap.get(place.getPlaceId());

            //방문 안했을 때
            if (content == null) {
                place.setMyStatus(null);
                continue;
            }

            //가장 최근에 방문한 시간을 방명록 내용 생성 시간으로 세팅
            place.setLastVisitedAt(content.createdAt());

            if (ui == ShowUiType.LIST) {
                place.setMyStatus(MyContentStatus.VISITED);
                place.setMyContent(content.content());
            } else if (ui == ShowUiType.MAP) {
                if (nearest != null &&
                        place.getPlaceId().equals(nearest.getPlaceId())) {

                    place.setMyStatus(MyContentStatus.RECENT);
                    place.setMyContent(content.content());
                } else {
                    place.setMyStatus(MyContentStatus.VISITED);
                }
            }
        }

        return places;
    }

    @Transactional(readOnly = true)
    public List<LatestContentResponse> getLatestContent(Long placeId) {
        return contentRepository.findRandomContents(placeId)
                .stream()
                .map(LatestContentResponse::from)
                .toList();
    }
}
