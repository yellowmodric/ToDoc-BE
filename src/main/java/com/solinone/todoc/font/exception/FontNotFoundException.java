package com.solinone.todoc.font.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class FontNotFoundException extends CustomException {
  public FontNotFoundException() {
    super(ErrorCode.FONT_NOT_FOUND);
  }
}
