package com.solinone.todoc.content.infrastructure;

import com.solinone.todoc.content.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    int countByBoardBoardId(Long boardId);

    List<Content> findAllByBoardBoardId(Long boardId);
}
