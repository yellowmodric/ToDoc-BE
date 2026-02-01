package com.solinone.todoc.infrastructure.business.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class InvalidBusinessNumberException extends CustomException {
    public InvalidBusinessNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
