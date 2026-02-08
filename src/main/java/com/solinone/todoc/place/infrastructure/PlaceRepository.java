package com.solinone.todoc.place.infrastructure;

import com.solinone.todoc.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByBusinessNumberAndAddress(String businessNumber, String address);
    List<Place> findByUser_UserIdOrderByCreatedAtDesc(Long userId);

    @Query(value = """
    select
        p.place_id   as placeId,
        p.place_name as placeName,
        p.latitude   as latitude,
        p.longitude  as longitude,
        count(c.content_id) as contentCount,
        p.address as address,
        (6371000 * acos(
            least(1.0, greatest(-1.0,
                cos(radians(:lat)) * cos(radians(p.latitude))
                * cos(radians(p.longitude) - radians(:lng))
                + sin(radians(:lat)) * sin(radians(p.latitude))
            ))
        )) as distance
    from places p
    left join boards b
        on b.place_id = p.place_id
    left join contents c
        on c.board_id = b.board_id
    group by p.place_id, p.place_name, p.latitude, p.longitude
    having
        (6371000 * acos(
            least(1.0, greatest(-1.0,
                cos(radians(:lat)) * cos(radians(p.latitude))
                * cos(radians(p.longitude) - radians(:lng))
                + sin(radians(:lat)) * sin(radians(p.latitude))
            ))
        )) <= :radius
    order by distance
    """, nativeQuery = true)
    List<PlaceMapProjection> findNearbyPlaces(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") int radius
    );

    List<Place> findByUserUserId(Long userId);
}
