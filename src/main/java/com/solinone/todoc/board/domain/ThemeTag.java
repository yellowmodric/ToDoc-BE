package com.solinone.todoc.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theme_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
}
