package com.solinone.todoc.font.application.ai;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FontReasonPromptFactory {
    public String create(
            String input,
            List<FontWithCategory> fonts
    ) {
        return """
                너는 폰트 추천 결과에 대해
                사용자에게 보여줄 짧은 설명 문구를 작성하는 역할이다.
                
                아래 사용자 입력과 폰트 정보를 바탕으로,
                각 폰트가 왜 어울리는지
                자연스럽고 부드러운 한국어 한 문장으로 설명하라.
                
                [작성 규칙]
                - 각 폰트마다 한 문장씩 작성
                - 20자 이상 30자 이하
                - 과장된 표현은 피할 것
                - 마케팅 문구처럼 부드럽게 작성
                - 존댓말 사용
                - 설명은 태그의 성격을 중심으로 작성
                
                [사용자 입력]
                %s
                
                [추천된 폰트 목록]
                %s
                
                [출력 형식]
                - 반드시 아래 JSON 형식만 출력하세요.
                  - 마크다운 사용 금지
                  - ``` 사용 금지
                  - 설명 문장 추가 금지
                
                - 형식:
                {
                  "reasons": {
                    "폰트명1": "설명 문장",
                    "폰트명2": "설명 문장"
                  }
                }
                - 다른 설명 없이 결과만 출력
                """.formatted(input, fonts);
    }

    private String formatFonts(List<FontWithCategory> fonts) {
        StringBuilder sb = new StringBuilder();
        int index = 1;

        for (FontWithCategory font : fonts) {
            sb.append(index++)
                    .append(". ")
                    .append(font.fontName())
                    .append("\n   - 태그: ")
                    .append(font.category().name())
                    .append("\n");
        }

        return sb.toString();
    }
}
