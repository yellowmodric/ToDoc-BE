package com.solinone.todoc.font.application;

import com.solinone.todoc.font.application.ai.PromptFactory;
import com.solinone.todoc.font.domain.AnalysisType;
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
    private final PromptFactory promptFactory;

    public List<FontCategory> analyze(String input, AnalysisType type) {
        String prompt = promptFactory.create(input, type);

        try {
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            FontCategoryAnalysisResponse result =
                    objectMapper.readValue(response, FontCategoryAnalysisResponse.class);
            return result.getCategories();
        } catch (Exception e) {
            //AI 실패 시 fallback
            return List.of(FontCategory.MODERN, FontCategory.RIGID);
        }
    }
}