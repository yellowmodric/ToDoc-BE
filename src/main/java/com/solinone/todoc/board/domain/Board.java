package com.solinone.todoc.board.domain;

import com.solinone.todoc.global.common.BaseEntity;
import com.solinone.todoc.place.domain.Place;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @Column
    private String qrUrl;

    public static Board create(Place place, Theme theme, String qrUrl) {
        Board board = new Board();
        board.place = place;
        board.theme = theme;
        board.qrUrl = qrUrl;
        return board;
    }
}
