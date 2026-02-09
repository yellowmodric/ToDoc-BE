package com.solinone.todoc.content.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CursorResponse<T>{
    private List<T> content;
    private Long nextCursor;
    private boolean hasNext;
    private long totalElements;

    public static <T> CursorResponse<T> of(List<T> content, Long nextCursor, boolean isLast, long totalElements) {
        return CursorResponse.<T>builder()
                .content(content)
                .nextCursor(nextCursor)
                .hasNext(!isLast)
                .totalElements(totalElements)
                .build();
    }
}
