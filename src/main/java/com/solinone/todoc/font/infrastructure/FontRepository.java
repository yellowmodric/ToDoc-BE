package com.solinone.todoc.font.infrastructure;

import com.solinone.todoc.font.domain.Font;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FontRepository extends JpaRepository<Font, Long> {
}
