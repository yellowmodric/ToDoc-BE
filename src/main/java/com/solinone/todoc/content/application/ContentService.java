package com.solinone.todoc.content.application;

import com.solinone.todoc.board.domain.Board;
import com.solinone.todoc.board.exception.BoardNotFoundException;
import com.solinone.todoc.board.infrastructure.BoardRepository;
import com.solinone.todoc.content.domain.Content;
import com.solinone.todoc.content.dto.request.ContentCreateRequest;
import com.solinone.todoc.content.dto.response.ContentCreateResponse;
import com.solinone.todoc.content.exception.ContentAccessDeniedException;
import com.solinone.todoc.content.infrastructure.ContentRepository;
import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.exception.FontNotFoundException;
import com.solinone.todoc.font.infrastructure.FontRepository;
import com.solinone.todoc.infrastructure.sse.SseEmitterService;
import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.exception.PlaceNotFoundException;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import com.solinone.todoc.user.domain.User;
import com.solinone.todoc.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {

    private final BoardRepository boardRepository;
    private final FontRepository fontRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final PlaceRepository placeRepository;
    private final SseEmitterService sseEmitterService;

    @Transactional
    public ContentCreateResponse createContent(Long placeId, ContentCreateRequest request, Long userId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);

        if (Objects.equals(place.getUser().getUserId(), userId)) {
            throw new ContentAccessDeniedException();
        }

        Board board = boardRepository.findByPlacePlaceId(placeId)
                .orElseThrow(BoardNotFoundException::new);

        Font font = fontRepository.findById(request.getFontId())
                .orElseThrow(FontNotFoundException::new);

        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        Content content = Content.create(board, user, font, request.getContent(), request.getThemeUrl());
        Content savedContent = contentRepository.save(content);

        int orderNumber = contentRepository.countByBoardBoardId(board.getBoardId());
        String placeName = place.getPlaceName();

        sseEmitterService.sendNewContent(placeId, savedContent);

        log.info("방명록 작성 완료 - contentId: {}, placedId: {}, orderNumber: {}, placeName: {}",
                savedContent.getContentId(), placeId, orderNumber, placeName);

        return ContentCreateResponse.from(savedContent, orderNumber, placeName);
    }
}
