package com.solinone.todoc.content.dto.response;

import java.time.LocalDateTime;

public record MyLatestContentResponse(
        Long contentId,
        Long placeId,
        String content,
        LocalDateTime createdAt
) {}
