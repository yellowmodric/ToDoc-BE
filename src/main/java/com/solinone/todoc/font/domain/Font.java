package com.solinone.todoc.font.domain;

import com.solinone.todoc.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fonts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Font extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fontId;

    @Column(nullable = false)
    private String fontName;

    @Column(nullable = false)
    private String fontNameEng;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FontField field;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FontCategory category;
}
