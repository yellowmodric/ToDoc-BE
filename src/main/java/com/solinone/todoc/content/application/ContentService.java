package com.solinone.todoc.content.application;

import com.solinone.todoc.board.domain.Board;
import com.solinone.todoc.board.dto.response.*;
import com.solinone.todoc.board.exception.BoardNotFoundException;
import com.solinone.todoc.board.infrastructure.BoardRepository;
import com.solinone.todoc.content.domain.Content;
import com.solinone.todoc.content.dto.request.ContentCreateRequest;
import com.solinone.todoc.content.dto.response.ContentCreateResponse;
import com.solinone.todoc.content.dto.response.CursorResponse;
import com.solinone.todoc.content.dto.response.PageResponse;
import com.solinone.todoc.content.exception.ContentAccessDeniedException;
import com.solinone.todoc.content.exception.ContentDeleteDeniedException;
import com.solinone.todoc.content.exception.ContentNotFoundException;
import com.solinone.todoc.content.exception.ContentProviderAccessDeniedException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteContents(List<Long> contentIds, Long userId) {

        if (contentIds == null || contentIds.isEmpty()) {
            throw new ContentNotFoundException();
        }

        List<Content> contents = contentRepository.findAllById(contentIds);

        if (contents.size() != contentIds.size()) {
            throw new ContentNotFoundException();
        }

        for (Content content : contents) {
            Long placeOwnerId = content.getBoard().getPlace().getUser().getUserId();

            if (!Objects.equals(placeOwnerId, userId)) {
                throw new ContentDeleteDeniedException();
            }
        }
        contentRepository.deleteAll(contents);
        log.info("방명록 일괄 삭제 완료 - userId: {}, 삭제 개수: {}", userId, contents.size());
    }

    @Transactional(readOnly = true)
    public ProviderContentsResponse getPlaceContents(Long placeId, Long userId, Integer page, Long cursor, int size, String sort) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);

        if (!Objects.equals(place.getUser().getUserId(), userId)) {
            throw new ContentProviderAccessDeniedException();
        }

        Board board = boardRepository.findByPlacePlaceId(placeId)
                .orElseThrow(BoardNotFoundException::new);

        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Object contents;
        if(page != null) {
            //웹 페이징
            contents = getContentsByPaging(board.getBoardId(), page, size, direction);
        } else if (cursor != null) {
            //모바일 커서 2번쨰 이후
            contents = getContentsByCursor(board.getBoardId(), cursor, size, direction);
        } else {
            //모바일 첫 요청
            contents = getContentsFirstPage(board.getBoardId(), size, direction);
        }


        List<Place> places = placeRepository.findAllByUserUserId(userId);
        List<PlaceWithBoardSummary> placeSummaries = places.stream()
                .map(p -> {
                    boolean hasBoard = boardRepository.existsByPlacePlaceId(p.getPlaceId());
                    return PlaceWithBoardSummary.of(p, hasBoard);
                })
                .collect(Collectors.toList());

        log.info("방명록 조회 완료 - placeId: {}, userId: {}, 전체 가게 수: {}", placeId, userId, places.size());

        return ProviderContentsResponse.of(
                PlaceInfo.of(place.getPlaceId(), place.getPlaceName()),
                contents,
                placeSummaries
        );
    }

    private CursorResponse<ContentResponse> getContentsFirstPage(Long boardId, int size, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(direction, "contentId"));
        Page<Content> page = contentRepository.findAllByBoardBoardId(boardId, pageable);

        long totalElements = contentRepository.countByBoardBoardId(boardId);

        List<ContentResponse> contentResponses = page.getContent().stream()
                .map(ContentResponse::from)
                .collect(Collectors.toList());

        Long nextCursor = contentResponses.isEmpty() ? null : contentResponses.get(contentResponses.size() - 1).getContentId();

        log.info("방명록 첫 페이지 조회 - boardId: {}, size: {}, hasNext: {} ",  boardId, size, !page.isLast());

        return CursorResponse.of(contentResponses, nextCursor, page.isLast(), totalElements);
    }

    private PageResponse<ContentResponse> getContentsByPaging(Long boardId, Integer page, int size, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size,
                Sort.by(direction, "createdAt"));

        Page<Content> contents = contentRepository.findAllByBoardBoardId(boardId, pageable);

        Page<ContentResponse> contentResponses = contents.map(ContentResponse::from);

        log.info("방명록 페이징 조회 - boardId: {}, page: {}, size: {}, sort: {}", boardId, page, size, direction);
        return PageResponse.of(contentResponses);
    }

    //모바일 커서 기반 조회
    private CursorResponse<ContentResponse> getContentsByCursor(
            Long boardId, Long cursor, int size, Sort.Direction direction) {
        long totalElements = contentRepository.countByBoardBoardId(boardId);

        List<Content> contents;
        if (direction == Sort.Direction.DESC) {
            contents = contentRepository.findByBoardBoardIdAndContentIdLessThan(
                    boardId, cursor,
                    PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "contentId")));
        } else {
            contents = contentRepository.findByBoardBoardIdAndContentIdGreaterThan(
                    boardId, cursor, PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "contentId")));
        }

        List<ContentResponse> contentResponses = contents.stream()
                .map(ContentResponse::from)
                .collect(Collectors.toList());

        Long nextCursor = contents.isEmpty() ? null : contents.get(contents.size() - 1).getContentId();

        boolean isLast = contents.size() < size;

        log.info("방명록 커서 조회 - boardId: {}, cursor: {}, size: {}, hasNext: {}", boardId, cursor, size, !isLast);

        return CursorResponse.of(contentResponses, nextCursor, isLast, totalElements);
    }
}
