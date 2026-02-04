package com.solinone.todoc.place.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class PlaceNotFoundException extends CustomException {
    public PlaceNotFoundException() {
        super(ErrorCode.PLACE_NOT_FOUND);
    }
}
