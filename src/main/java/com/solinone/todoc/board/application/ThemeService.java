package com.solinone.todoc.board.application;

import com.solinone.todoc.board.dto.response.ThemeResponse;
import com.solinone.todoc.board.infrastructure.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<ThemeResponse> getAllThemes() {
        return themeRepository.findAllWithTags().stream()
                .map(ThemeResponse::from)
                .collect(Collectors.toList());
    }
}
