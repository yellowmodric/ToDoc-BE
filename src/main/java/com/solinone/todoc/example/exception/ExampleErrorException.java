package com.solinone.todoc.example.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class ExampleErrorException extends CustomException {
    public ExampleErrorException() {
        super(ErrorCode.EXAMPLE_ERROR);
    }
}
