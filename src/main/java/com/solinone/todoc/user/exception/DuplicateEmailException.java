package com.solinone.todoc.user.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
