package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ContentAccessDeniedException extends CustomException {
    public ContentAccessDeniedException() {
        super(ErrorCode.CONTENT_ACCESS_DENIED);
    }
}
