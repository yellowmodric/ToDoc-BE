package com.solinone.todoc.infrastructure.sse;

import com.solinone.todoc.content.domain.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SseEmitterService {

    private static final Long DEFAULT_TIMEOUT = 60 * 60 * 1000L;

    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    /**
     * SSE 연결 생성
     */
    public SseEmitter createEmitter(Long placeId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // placeId별 emitter 리스트에 추가
        emitters.computeIfAbsent(placeId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        //연결 종료 시 제거
        emitter.onCompletion(() -> removeEmitter(placeId, emitter));
        emitter.onTimeout(() -> removeEmitter(placeId, emitter));
        emitter.onError(e -> removeEmitter(placeId, emitter));

        log.info("SSE 연결 생성 - placeId: {}, 현재 연결 수: {}", placeId, emitters.get(placeId).size());

        //연결 직후 더미 이벤트 전송 (연결 확인용)
        try {
            emitter.send(SseEmitter.event()
                            .name("connect")
                    .data("Connected to place: " + placeId));
        } catch (IOException e) {
            log.error("SSE 연결 확인 이벤트 전송 실패 - placeId: {}", placeId, e);
            removeEmitter(placeId, emitter);
        }
        return emitter;
    }

    /**
     * 새 방명록 작성 시 해당 placeId의 모든 연결 전송
     */
    public void sendNewContent(Long placeId, Content content) {
        List<SseEmitter> placeEmitters = emitters.get(placeId);

        if (placeEmitters == null || placeEmitters.isEmpty()) {
            log.info("SSE 전송 대상 없음 - placeId: {}", placeId);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");

        //전송 데이터 구성
        Map<String, Object> data = Map.of(
                "contentId", content.getContentId(),
                "content", content.getContent(),
                "contentLength", content.getContent().length(),
                "fontId", content.getFont().getFontId(),
                "themeUrl", content.getThemeUrl(),
                "createdAt", content.getCreatedAt().format(formatter)
        );

        //연결된 모든 클라이언트 전송
        int successCount = 0;
        int failCount = 0;

        for (SseEmitter emitter : placeEmitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("new-content")
                        .data(data));
                successCount++;
            } catch (IOException e) {
                log.error("SSE 전송 실패 - placeId: {}, contnetId: {}", placeId, content.getContentId(), e);
                removeEmitter(placeId, emitter);
                failCount++;
            }
        }
        log.info("SSE 전송 완료 - placeId: {}, contentId: {}, 성공: {}, 실패: {} ",
                placeId, content.getContentId(), successCount, failCount);
    }

    //Emitter 제거
    private void removeEmitter(Long placeId, SseEmitter emitter) {
        List<SseEmitter> placeEmitters = emitters.get(placeId);
        if (placeEmitters != null) {
            placeEmitters.remove(emitter);
            log.info("SSE 연결 제거 - placeId: {}, 남은 연결 수: {}",  placeId, placeEmitters.size());

            //연결이 없으면 Map에서도 제거
            if (placeEmitters.isEmpty()) {
                emitters.remove(placeId);
            }
        }
    }
}
