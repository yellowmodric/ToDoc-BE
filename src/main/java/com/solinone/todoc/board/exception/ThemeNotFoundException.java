package com.solinone.todoc.board.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ThemeNotFoundException extends CustomException {
    public ThemeNotFoundException() {
        super(ErrorCode.THEME_NOT_FOUND);
    }
}
