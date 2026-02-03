package com.solinone.todoc.font.application;

import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.dto.response.FontCategoryAnalysisResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FontEmotionAnalysisService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public List<FontCategory> analyze(String content) {
        String prompt = buildPrompt(content);

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        log.info("[FontEmotionAnalysis] input='{}'", content);
        log.info("[FontEmotionAnalysis] raw AI response={}", response);
        try {
            FontCategoryAnalysisResponse result =
                    objectMapper.readValue(response, FontCategoryAnalysisResponse.class);
            return result.getCategories();
        } catch (Exception e) {
            //AI 실패 시 fallback
            return List.of(FontCategory.MODERN, FontCategory.RIGID);
        }
    }

    private String buildPrompt(String content) {
        return """
        너는 텍스트의 감정과 뉘앙스를 정확하게 파악하여 폰트 스타일을 추천하는 전문가다.
        사용자의 입력 내용에 따라 가장 적절한 FontCategory를 선택하라.

        [분류 가이드라인]
        - CUTE: 귀엽고 아기자기하며 긍정적인 분위기
        - RIGID: 딱딱하고, 불만족스럽거나, 격식을 차린 차가운 분위기 (예: 컴플레인, 경고)
        - LIGHT: 가볍고 경쾌하거나 일상적인 분위기
        - SERIOUS: 진지하고, 무거운, 비판적이거나 신중한 느낌
        - UNIQUE: 평범하지 않은, 개성이 강한, 실험적인 느낌
        - RETRO: 옛스러운, 복고풍의, 향수를 불러일으키는 느낌
        - EMOTIONAL: 감수성이 풍부한, 마음을 울리는, 따뜻한 느낌
        - ELEGANT: 고급스럽고, 우아하며, 격식 있는 느낌
        - MODERN: 세련되고, 깔끔하며, 도시적인 느낌
        - LYRICAL: 시적이고, 서정적이며, 노래 가사 같은 부드러운 느낌
        - SCARY: 공포스러운, 기괴한, 날카롭거나 위협적인 느낌

        [매칭 규칙]
        1. 텍스트가 부정적이거나 비판적일 경우 CUTE, EMOTIONAL, LIGHT는 지양하라.
        2. "별로다", "최악이다"와 같은 불만은 RIGID, SERIOUS, MODERN 계열을 우선 고려하라.
        3. 반드시 제공된 Enum 값 내에서만 선택하라.

        [출력 형식]
        - JSON 형식으로만 응답
        - 형식: { "categories": ["TAG1", "TAG2", "TAG3"] }
        - 다른 설명 없이 결과만 출력

        [텍스트]
        "%s"
    """.formatted(content);
    }
}
