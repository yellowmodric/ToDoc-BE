package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ContentNotFoundException extends CustomException {
    public ContentNotFoundException() {
        super(ErrorCode.CONTENT_NOT_FOUND);
    }
}
