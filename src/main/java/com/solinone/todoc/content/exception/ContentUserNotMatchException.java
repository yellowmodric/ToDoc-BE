package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ContentUserNotMatchException extends CustomException {
    public ContentUserNotMatchException() {
        super(ErrorCode.CONTENT_USER_NOT_MATCH);
    }
}
