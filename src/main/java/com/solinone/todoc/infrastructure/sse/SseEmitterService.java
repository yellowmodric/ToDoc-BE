package com.solinone.todoc.infrastructure.sse;

import com.solinone.todoc.content.domain.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SseEmitterService {

    private static final Long DEFAULT_TIMEOUT = 60 * 60 * 1000L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Map<Long, Set<SseEmitter>> emitters = new ConcurrentHashMap<>();

    /**
     * SSE 연결 생성
     */
    public SseEmitter createEmitter(Long placeId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // placeId에 해당하는 emitter 리스트에 추가
        //computeIfAbsent: key 없으면 새 리스트 생성 후 추가
        emitters.computeIfAbsent(placeId, k -> ConcurrentHashMap.newKeySet()).add(emitter);

        //연결 종료 시 제거
        emitter.onCompletion(() -> removeEmitter(placeId, emitter));
        emitter.onTimeout(() -> removeEmitter(placeId, emitter));
        emitter.onError(e -> {
            log.debug("SSE 에러 발생 - placeId: {}", placeId, e);
            removeEmitter(placeId, emitter);
        });

        //연결 직후 더미 이벤트 전송 (연결 확인용)
        try {
            emitter.send(SseEmitter.event()
                            .name("connect")
                    .data("Connected to place: " + placeId));
            log.debug("SSE 연결 생성 - placeId: {}, 현재 연결 수: {} ", placeId, getEmitterCount(placeId));
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
        Map<String, Object> data = new HashMap<>();
            data.put("contentId", content.getContentId());
            data.put("content", content.getContent());
            data.put("contentLength", content.getContent().length());
            data.put("fontId", content.getFont().getFontId());
            data.put("themeUrl", content.getThemeUrl());
            data.put("createdAt", content.getCreatedAt().format(formatter));

        sendToPlace(placeId, "new-content", data);
    }

    public void sendBoostedContent(Long placeId, Content content) {
        Map<String, Object> data = new HashMap<>();
            data.put("contentId", content.getContentId());
            data.put("content", content.getContent());
            data.put("fontId",  content.getFont().getFontId());
            data.put("themeUrl", content.getThemeUrl());
            data.put("boostedAt", LocalDateTime.now().format(formatter));

        sendToPlace(placeId, "boost-content", data);
    }

    private void sendToPlace(Long placeId, String eventName, Object data) {
        Set<SseEmitter> placeEmitters = emitters.get(placeId);

        if (placeEmitters == null || placeEmitters.isEmpty()) {
            return;
        }

        int successCount = 0;
        Set<SseEmitter> deadEmitters = ConcurrentHashMap.newKeySet();

        //모든 연결에 이벤트 전송
        for (SseEmitter emitter : placeEmitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
                successCount++;
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        }

        //실패한 연결 일괄 제거
        deadEmitters.forEach(emitter -> removeEmitter(placeId, emitter));

        if (!deadEmitters.isEmpty()) {
            log.debug("SSE 전송 - placeId: {}, 성공: {}, 실패: {}",
                    placeId, successCount, deadEmitters.size());
        }
    }

    //Emitter 제거
    private void removeEmitter(Long placeId, SseEmitter emitter) {
        Set<SseEmitter> placeEmitters = emitters.get(placeId);
        if (placeEmitters != null) {
            placeEmitters.remove(emitter);

            //연결이 없으면 Map에서도 제거
            if (placeEmitters.isEmpty()) {
                emitters.remove(placeId);
            }
        }
    }

    private int getEmitterCount(Long placeId) {
        Set<SseEmitter> placeEmitters = emitters.get(placeId);
        return placeEmitters != null ? placeEmitters.size() : 0;
    }
}
