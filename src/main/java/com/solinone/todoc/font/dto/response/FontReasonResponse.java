package com.solinone.todoc.font.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
//AI 응답 파싱용 response
public class FontReasonResponse {
    private Map<String, String> reasons;
}
