package com.solinone.todoc.board.infrastructure;

import com.solinone.todoc.board.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("SELECT DISTINCT t FROM Theme t LEFT JOIN FETCH t.themeTags tt LEFT JOIN FETCH tt.tag")
    List<Theme> findAllWithTags();
}
