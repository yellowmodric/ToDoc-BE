package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ContentPlaceNotMatchException extends CustomException {
    public ContentPlaceNotMatchException() {
        super(ErrorCode.CONTENT_PLACE_NOT_MATCH);
    }
}
