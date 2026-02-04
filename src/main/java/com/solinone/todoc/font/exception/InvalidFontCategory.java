package com.solinone.todoc.font.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class InvalidFontCategory extends CustomException {
    public InvalidFontCategory(String message) {
        super(ErrorCode.INVALID_FONT_CATEGORY);
    }
}
