package com.solinone.todoc.content.application;

import com.solinone.todoc.content.dto.response.MyContentResponse;
import com.solinone.todoc.content.infrastructure.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyContentService {
    private final ContentRepository contentRepository;

    public List<MyContentResponse> getMyContents(Long placeId, Long userId) {
        return contentRepository
                .findByBoard_Place_PlaceIdAndUser_UserIdOrderByCreatedAtDesc(
                        placeId, userId
                )
                .stream()
                .map(MyContentResponse::from)
                .toList();
    }
}
