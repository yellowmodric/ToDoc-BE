package com.solinone.todoc.board.application;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ThemeUrlResolver {
    private static final long DEFAULT_THEME_ID = 5L;
    private static String BASE_URL =
            "https://todocbucket.s3.ap-northeast-2.amazonaws.com/theme/";

    public String resolve(Long themeId) {
        if (themeId == null) {
            throw new IllegalArgumentException("themeId cannot be null");
        }

        if (themeId == DEFAULT_THEME_ID) {
            return build(themeId, 1);
        }

        int idx = ThreadLocalRandom.current().nextInt(1, 5);
        return build(themeId, idx);
    }

    private String build(Long themeId, int idx) {
        return BASE_URL + themeId + "/" + idx + ".png";
    }
}
