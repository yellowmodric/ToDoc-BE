package com.solinone.todoc.user.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class InvalidCredentialException extends CustomException {
    public InvalidCredentialException() {
        super(ErrorCode.INVALID_CREDENTIALS_EXCEPTION);
    }
}
