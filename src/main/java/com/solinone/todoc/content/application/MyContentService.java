package com.solinone.todoc.content.application;

import com.solinone.todoc.content.dto.response.MyContentResponse;
import com.solinone.todoc.content.dto.response.MyPageContentResponse;
import com.solinone.todoc.content.infrastructure.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyContentService {
    private final ContentRepository contentRepository;

    //특정 매장 내 방명록
    @Transactional(readOnly = true)
    public List<MyContentResponse> getMyContents(Long placeId, Long userId) {
        return contentRepository
                .findByBoard_Place_PlaceIdAndUser_UserIdOrderByCreatedAtDesc(
                        placeId, userId
                )
                .stream()
                .map(MyContentResponse::from)
                .toList();
    }

    //마이페이지에서 내 방명록 전체 조회
    @Transactional(readOnly = true)
    public List<MyPageContentResponse> getMyPageContents(Long userId) {
        return contentRepository
                .findByUser_UserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(MyPageContentResponse::from)
                .toList();
    }
}
