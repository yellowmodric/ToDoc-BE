package com.solinone.todoc.place.infrastructure;

import com.solinone.todoc.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByBusinessNumberAndAddress(String businessNumber, String address);
    List<Place> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
