package com.solinone.todoc.board.application;

import com.solinone.todoc.board.domain.Board;
import com.solinone.todoc.board.domain.Theme;
import com.solinone.todoc.board.dto.request.BoardCreateRequest;
import com.solinone.todoc.board.exception.BoardAlreadyExistException;
import com.solinone.todoc.board.exception.ThemeNotFoundException;
import com.solinone.todoc.board.infrastructure.BoardRepository;
import com.solinone.todoc.board.infrastructure.ThemeRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final PlaceRepository placeRepository;
    private final ThemeRepository themeRepository;
    private final S3Uploader s3Uploader;

    @Value("${app.qr.base-url}")
    private String qrBaseUrl;

    @Transactional
    public void createBoard( BoardCreateRequest request, Long userId) {

        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(PlaceNotFoundException::new);

        if (!place.getUser().getUserId().equals(userId)) {
            throw new PlaceUserMismatchException();
        }

        if (boardRepository.existsByPlaceId(request.getPlaceId())) {
            throw new BoardAlreadyExistException();
        }

        Theme theme = themeRepository.findById(request.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);

        //QR코드 생성
        String qrTargetUrl = qrBaseUrl + "/" + request.getPlaceId();
        byte[] qrImageBytes = QrCodeGenerator.generateQrCode(qrTargetUrl, 300, 300);
        String qrUrl = s3Uploader.uploadQrImage(request.getPlaceId(), qrImageBytes);

        Board board = Board.create(place, theme, qrUrl);
        Board savedBoard = boardRepository.save(board);

        log.info("방명록 판 생성 완료 - boardId: {}, placeId: {}, userId: {}, qrUrl: {}",
                savedBoard.getBoardId(), request.getPlaceId(), userId, qrUrl);
    }
}
