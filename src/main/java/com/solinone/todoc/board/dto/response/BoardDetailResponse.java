package com.solinone.todoc.board.dto.response;

import com.solinone.todoc.board.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardDetailResponse {

    private Long boardId;
    private String themeName;
    private String boardColor;
    private String qrUrl;
    private List<ContentResponse> contents;

    public static BoardDetailResponse of(Board board, List<ContentResponse> contents) {
        return BoardDetailResponse.builder()
                .boardId(board.getBoardId())
                .themeName(board.getTheme().getThemeName())
                .boardColor(board.getBoardColor())
                .qrUrl(board.getQrUrl())
                .contents(contents)
                .build();
    }
}
