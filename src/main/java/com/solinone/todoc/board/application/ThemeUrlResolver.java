package com.solinone.todoc.board.application;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ThemeUrlResolver {
    private static final long DEFAULT_THEME_ID = 5L;
    private static String BASE_URL =
            "https://todocbucket.s3.ap-northeast-2.amazonaws.com/theme/";

    //테마별 이미지 개수
    private static final Map<Long, Integer> THEME_IMAGE_COUNT = Map.of(
            5L, 1,
            7L, 12
    );

    public String resolve(Long themeId) {
        if (themeId == null) {
            throw new IllegalArgumentException("themeId cannot be null");
        }

        if (themeId == DEFAULT_THEME_ID) {
            return build(themeId, 1);
        }

        int maxCount = THEME_IMAGE_COUNT.getOrDefault(themeId, 4);

        int idx = ThreadLocalRandom.current().nextInt(1, maxCount + 1);

        return build(themeId, idx);
    }

    private String build(Long themeId, int idx) {
        return BASE_URL + themeId + "/" + idx + ".png";
    }
}
