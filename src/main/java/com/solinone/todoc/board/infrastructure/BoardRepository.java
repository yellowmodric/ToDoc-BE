package com.solinone.todoc.board.infrastructure;

import com.solinone.todoc.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 특정 장소에 이미 방명록 판이 존재하는지 확인
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Board b WHERE b.place.placeId = :placeId")
    boolean existsByPlaceId(@Param("placeId") Long placeId);
}
