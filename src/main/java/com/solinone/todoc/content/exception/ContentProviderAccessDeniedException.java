package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ContentProviderAccessDeniedException extends CustomException {
    public ContentProviderAccessDeniedException() {
        super(ErrorCode.CONTENT_PROVIDER_ACCESS_DENIED);
    }
}
