package com.solinone.todoc.font.application;

import com.solinone.todoc.font.domain.Font;
import com.solinone.todoc.font.domain.FontCategory;
import com.solinone.todoc.font.dto.response.FontResponse;
import com.solinone.todoc.font.infrastructure.FontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FontService {
    private final FontRepository fontRepository;

    public List<FontResponse> getAllFonts() {
        return fontRepository.findAll().stream()
                .map(FontResponse::from)
                .toList();
    }

    public List<FontResponse> getFontsByCategory(FontCategory category) {
        return fontRepository.findByCategory(category).stream()
                .map(FontResponse::from)
                .toList();
    }
}
