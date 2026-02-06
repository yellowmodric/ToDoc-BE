package com.solinone.todoc.board.infrastructure;

import com.solinone.todoc.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 특정 장소에 이미 방명록 판이 존재하는지 확인
    boolean existsByPlacePlaceId(@Param("placeId") Long placeId);

    Optional<Board> findByPlacePlaceId(Long placeId);
}
