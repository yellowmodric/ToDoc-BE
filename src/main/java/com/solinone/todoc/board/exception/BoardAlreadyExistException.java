package com.solinone.todoc.board.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class BoardAlreadyExistException extends CustomException {
    public BoardAlreadyExistException() {
        super(ErrorCode.BOARD_ALREADY_EXIST);
    }
}
