package com.solinone.todoc.place.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class DuplicatePlaceException extends CustomException {
    public DuplicatePlaceException() {
        super(ErrorCode.DUPLICATE_PLACE);
    }
}
