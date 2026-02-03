package com.solinone.todoc.font.application;

import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.infrastructure.FontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FontRecommendationService {
    private final FontRepository fontRepository;

    public List<Font> recommend(List<FontCategory> categories) {
        List<Font> candidates = fontRepository.findByCategoryIn(categories);

        Collections.shuffle(candidates);

        return candidates.stream()
                .limit(4)
                .toList();
    }
}
