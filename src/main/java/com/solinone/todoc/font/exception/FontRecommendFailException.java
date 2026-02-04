package com.solinone.todoc.font.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class FontRecommendFailException extends CustomException {
    public FontRecommendFailException(String message) {
        super(ErrorCode.FONT_RECOMMEND_FAIL);
    }
}
