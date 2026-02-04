package com.solinone.todoc.font.application;

import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.infrastructure.FontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FontRecommendationService {
    private static final int RECOMMEND_COUNT = 4;
    private static final int[] CATEGORY_QUOTA = {2, 1, 1};
    private final FontRepository fontRepository;

    public List<Font> recommend(List<FontCategory> categories) {
        List<Font> result = new ArrayList<>();

        //카테고리 우선순위 + 쿼터 적용
        for (int i=0; i<categories.size() && i < CATEGORY_QUOTA.length; i++) {
            FontCategory category = categories.get(i);
            int quota = CATEGORY_QUOTA[i];

            addFontsByCategory(result, category, quota);
        }

        //만약 추천 태그 데이터가 부족할 때
        if (result.size() < RECOMMEND_COUNT) {
            fillWithFallback(result, categories);
        }

        return result;
    }

    private void addFontsByCategory(
            List<Font> result,
            FontCategory category,
            int quota
    ) {
        if (result.size() >= RECOMMEND_COUNT) return;

        List<Font> fonts = fontRepository.findByCategory(category);
        Collections.shuffle(fonts);

        for (Font font : fonts) {
            if (result.size() >= RECOMMEND_COUNT) break;
            if (quota <= 0) break;

            if (!contains(result, font)) {
                result.add(font);
                quota--;
            }
        }
    }

    private boolean contains(List<Font> result, Font font) {
        return result.stream()
                .anyMatch(f -> f.getFontId().equals(font.getFontId()));
    }

    //전체 조회해서 아무거나 추천
    private void fillWithFallback(List<Font> result, List<FontCategory> categories) {
        for (FontCategory category : categories) {
            if (result.size() >= RECOMMEND_COUNT) break;

            List<Font> fonts = fontRepository.findByCategory(category);
            Collections.shuffle(fonts);

            for (Font font : fonts) {
                if (result.size() >= RECOMMEND_COUNT) break;
                if (!contains(result, font)) {
                    result.add(font);
                }
            }
        }
    }
}
