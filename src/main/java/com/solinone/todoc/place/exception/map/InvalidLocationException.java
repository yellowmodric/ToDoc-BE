package com.solinone.todoc.place.exception.map;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class InvalidLocationException extends CustomException {
    public InvalidLocationException() {
        super(ErrorCode.INVALID_LOCATION);
    }
}
