package com.solinone.todoc.font.exception;

import com.solinone.todoc.global.exception.CustomException;
import com.solinone.todoc.global.exception.ErrorCode;

public class FontReasonGenerateException extends CustomException {
  public FontReasonGenerateException() {
    super(ErrorCode.FONT_REASON_GENERATE_FAIL);
  }
}
