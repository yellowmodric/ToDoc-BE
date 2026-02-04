package com.solinone.todoc.place.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class PlaceUserMismatchException extends CustomException {
    public PlaceUserMismatchException() {
        super(ErrorCode.PLACE_USER_MISMATCH);
    }
}
