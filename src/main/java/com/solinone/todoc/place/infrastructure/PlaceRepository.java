package com.solinone.todoc.place.infrastructure;

import com.solinone.todoc.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByBusinessNumberAndAddress(String businessNumber, String address);
}
