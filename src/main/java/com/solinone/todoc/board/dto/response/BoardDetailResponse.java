package com.solinone.todoc.board.dto.response;

import com.solinone.todoc.board.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardDetailResponse {

    private Long boardId;
    private Long themeId;
    private String qrUrl;
    private String placeName;
    private List<ContentResponse> contents;

    public static BoardDetailResponse of(Board board, List<ContentResponse> contents) {
        return BoardDetailResponse.builder()
                .boardId(board.getBoardId())
                .themeId(board.getTheme().getThemeId())
                .qrUrl(board.getQrUrl())
                .placeName(board.getPlace().getPlaceName())
                .contents(contents)
                .build();
    }
}
