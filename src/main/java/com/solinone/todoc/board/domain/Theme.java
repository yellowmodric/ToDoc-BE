package com.solinone.todoc.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "themes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeId;

    @Column(nullable = false)
    private String themeName;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    private List<ThemeTag> themeTags = new ArrayList<>();
}
