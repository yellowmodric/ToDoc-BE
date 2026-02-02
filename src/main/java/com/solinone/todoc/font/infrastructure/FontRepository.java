package com.solinone.todoc.font.infrastructure;

import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.domain.FontCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FontRepository extends JpaRepository<Font, Long> {
    List<Font> findByCategory(FontCategory category);
}
