package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ContentDeleteDeniedException extends CustomException {
    public ContentDeleteDeniedException() {
        super(ErrorCode.CONTENT_DELETE_DENIED);
    }
}
