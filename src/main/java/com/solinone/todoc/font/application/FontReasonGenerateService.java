package com.solinone.todoc.font.application;

import com.solinone.todoc.font.application.ai.FontReasonPromptFactory;
import com.solinone.todoc.font.application.ai.FontWithCategory;
import com.solinone.todoc.font.dto.response.FontReasonResponse;
import com.solinone.todoc.font.exception.FontReasonGenerateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FontReasonGenerateService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final FontReasonPromptFactory promptFactory;

    //추천된 폰트들에 대한 이유를 한 번의 AI 호출로 생성
    public Map<String, String> generateReasons(
            String input,
            List<FontWithCategory> fonts
    ) {
        String prompt = promptFactory.create(input, fonts);

        try {
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            FontReasonResponse parsed =
                    objectMapper.readValue(response, FontReasonResponse.class);

            return normalize(parsed.getReasons(), fonts);
        } catch (Exception e) {
            log.error("AI reason 생성 실패", e);
            throw new FontReasonGenerateException();
        }
    }

    //AI 응답 보정 - 이유 응답이 누락돼도 항상 모든 폰트에 이유 보장
    private Map<String, String> normalize(
            Map<String, String> reasons,
            List<FontWithCategory> fonts
    ) {
        Map<String, String> result = new LinkedHashMap<>();

        for (FontWithCategory font : fonts) {
            result.put(
                    font.fontName(),
                    reasons.getOrDefault(
                            font.fontName(),
                            defaultReason()
                    )
            );
        }

        return result;
    }

    private String defaultReason() {
        return "이 분위기에 자연스럽게 어울리는 폰트예요";
    }
}
