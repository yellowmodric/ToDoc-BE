package com.solinone.todoc.content.infrastructure;

import com.solinone.todoc.content.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT COUNT(c) FROM Content c WHERE c.board.boardId = :boardId")
    int countByBoardId(@Param("boardId") Long boardId);

}
