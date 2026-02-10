package com.solinone.todoc.infrastructure.sse;

import com.solinone.todoc.content.domain.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SseEmitterService {

    private static final Long DEFAULT_TIMEOUT = 60 * 60 * 1000L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    /**
     * SSE 연결 생성
     */
    public SseEmitter createEmitter(Long placeId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // placeId에 해당하는 emitter 리스트에 추가
        //computeIfAbsent: key 없으면 새 리스트 생성 후 추가
        emitters.computeIfAbsent(placeId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        //연결 종료 시 제거
        emitter.onCompletion(() -> removeEmitter(placeId, emitter));
        emitter.onTimeout(() -> removeEmitter(placeId, emitter));
        emitter.onError(e -> removeEmitter(placeId, emitter));

        //연결 직후 더미 이벤트 전송 (연결 확인용)
        try {
            emitter.send(SseEmitter.event()
                            .name("connect")
                    .data("Connected to place: " + placeId));
            log.info("SSE 연결 생성 - placeId: {}, 현재 연결 수: {} ", placeId, getEmitterCount(placeId));
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
        //전송 데이터 구성
        Map<String, Object> data = Map.of(
                "contentId", content.getContentId(),
                "content", content.getContent(),
                "contentLength", content.getContent().length(),
                "fontId", content.getFont().getFontId(),
                "themeUrl", content.getThemeUrl(),
                "createdAt", content.getCreatedAt().format(formatter)
        );
        sendToPlace(placeId, "new-content", data);
    }

    public void sendBoostedContent(Long placeId, Content content) {
        Map<String, Object> data = Map.of(
                "contentId", content.getContentId(),
                "content", content.getContent(),
                "fontId",  content.getFont().getFontId(),
                "themeUrl", content.getThemeUrl(),
                "boostedAt", LocalDateTime.now().format(formatter)
        );
        sendToPlace(placeId, "boost-content", data);
    }

    private void sendToPlace(Long placeId, String eventName, Object data) {
        List<SseEmitter> placeEmitters = emitters.get(placeId);

        if (placeEmitters == null || placeEmitters.isEmpty()) {
            log.info("전송할 SSE 연결 없음 - placeId: {}", placeId);
            return;
        }

        int successCount = 0;
        int failCount = 0;

        //모든 연결에 이벤트 전송
        for (SseEmitter emitter : placeEmitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
                successCount++;
            } catch (IOException e) {
                log.warn("SSE 전송 실패 - placeId: {}, event: {}", placeId, eventName, e);
                removeEmitter(placeId, emitter);
                failCount++;
            }
        }
        log.info("SSE 이벤트 전송 완료 - placeId: {}, event: {}, 성공: {}, 실패: {}",
                placeId, eventName, successCount, failCount);
    }

    //Emitter 제거
    private void removeEmitter(Long placeId, SseEmitter emitter) {
        List<SseEmitter> placeEmitters = emitters.get(placeId);
        if (placeEmitters != null) {
            placeEmitters.remove(emitter);
            log.debug("SSE 연결 제거 - placeId: {}, 남은 연결 수: {}",  placeId, placeEmitters.size());

            //연결이 없으면 Map에서도 제거
            if (placeEmitters.isEmpty()) {
                emitters.remove(placeId);
            }
        }
    }

    private int getEmitterCount(Long placeId) {
        List<SseEmitter> placeEmitters = emitters.get(placeId);
        return placeEmitters != null ? placeEmitters.size() : 0;
    }
}
