package com.solinone.todoc.place.exception.map;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class InvalidRadiusException extends CustomException {
    public InvalidRadiusException() {
        super(ErrorCode.INVALID_RADIUS);
    }
}
