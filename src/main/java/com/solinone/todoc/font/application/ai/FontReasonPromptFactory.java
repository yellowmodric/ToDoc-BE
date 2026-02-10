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
                사람이 자연스럽게 말해주는 것처럼
                부드러운 한국어 한 문장으로 설명하라.
                
                [작성 톤 가이드]
                - 안내문이나 설명문이 아닌, 추천 이유를 살짝 덧붙여 주는 말투
                - "~해서 잘 어울려요", "~한 분위기를 살려줘요" 등 문장 끝 표현은 자유롭게 변형할 것
                - 모든 문장이 같은 패턴으로 끝나지 않도록 주의
                - 폰트의 특징이 사용자 입력과 어떻게 어울리는지 설명할 것
                
                [작성 규칙]
                - 각 폰트마다 한 문장씩 작성
                - 28자 이상 40자 이하
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
