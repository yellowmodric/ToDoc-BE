package com.solinone.todoc.content.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class LocationOutOfRangeException extends CustomException {
    public LocationOutOfRangeException() {
        super(ErrorCode.LOCATION_OUT_OF_RANGE);
    }
}
