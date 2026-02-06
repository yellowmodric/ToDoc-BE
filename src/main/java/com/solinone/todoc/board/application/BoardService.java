package com.solinone.todoc.board.application;

import com.solinone.todoc.board.domain.Board;
import com.solinone.todoc.board.domain.Theme;
import com.solinone.todoc.board.dto.request.BoardCreateRequest;
import com.solinone.todoc.board.dto.response.ContentResponse;
import com.solinone.todoc.board.dto.response.PlaceWithBoardResponse;
import com.solinone.todoc.board.dto.response.ProviderHomeResponse;
import com.solinone.todoc.board.exception.BoardAlreadyExistException;
import com.solinone.todoc.board.exception.ThemeNotFoundException;
import com.solinone.todoc.board.infrastructure.BoardRepository;
import com.solinone.todoc.board.infrastructure.ThemeRepository;
import com.solinone.todoc.content.domain.Content;
import com.solinone.todoc.content.infrastructure.ContentRepository;
import com.solinone.todoc.infrastructure.s3.QrCodeGenerator;
import com.solinone.todoc.infrastructure.s3.S3Uploader;
import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.exception.PlaceNotFoundException;
import com.solinone.todoc.place.exception.PlaceUserMismatchException;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final PlaceRepository placeRepository;
    private final ThemeRepository themeRepository;
    private final S3Uploader s3Uploader;
    private final ContentRepository contentRepository;

    @Value("${app.qr.base-url}")
    private String qrBaseUrl;

    @Transactional
    public void createBoard( BoardCreateRequest request, Long userId) {

        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(PlaceNotFoundException::new);

        if (!place.getUser().getUserId().equals(userId)) {
            throw new PlaceUserMismatchException();
        }

        if (boardRepository.existsByPlacePlaceId(request.getPlaceId())) {
            throw new BoardAlreadyExistException();
        }

        Theme theme = themeRepository.findById(request.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);

        //QR코드 생성
        String qrTargetUrl = qrBaseUrl + "/" + request.getPlaceId();
        byte[] qrImageBytes = QrCodeGenerator.generateQrCode(qrTargetUrl, 300, 300);
        String qrUrl = s3Uploader.uploadQrImage(request.getPlaceId(), qrImageBytes);

        Board board = Board.create(place, theme, request.getBoardColor(), qrUrl);
        Board savedBoard = boardRepository.save(board);

        log.info("방명록 판 생성 완료 - boardId: {}, placeId: {}, userId: {}, qrUrl: {}",
                savedBoard.getBoardId(), request.getPlaceId(), userId, qrUrl);
    }

    public ProviderHomeResponse getProviderHome(Long userId) {
        List<Place> places = placeRepository.findByUserUserId(userId);

        List<PlaceWithBoardResponse> responses = new ArrayList<>();

        //첫번째 가게 정보 포함
        Place firstPlace = places.getFirst();
        Optional<Board> firstBoard = boardRepository.findByPlacePlaceId(firstPlace.getPlaceId());

        if (firstBoard.isPresent()) {
            List<Content> contents = contentRepository.findAllByBoardBoardId(firstBoard.get().getBoardId());
            List<ContentResponse> contentResponses = contents.stream()
                    .map(ContentResponse::from)
                    .collect(Collectors.toList());
            responses.add(PlaceWithBoardResponse.of(firstPlace, firstBoard.get(), contentResponses));
        } else {
            responses.add(PlaceWithBoardResponse.ofWithoutBoard(firstPlace));
        }

        //나머지 가게는 기본 정보만
        for (int i=1; i<places.size(); i++) {
            Place place = places.get(i);
            boolean hasBoard = boardRepository.existsByPlacePlaceId(place.getPlaceId());

            responses.add(PlaceWithBoardResponse.ofBasicInfo(place, hasBoard));
        }
        log.info("사장님 홈 조회 완료 - userId: {}, 가게 수: {}",  userId, places.size());

        return ProviderHomeResponse.of(responses);
    }
}
