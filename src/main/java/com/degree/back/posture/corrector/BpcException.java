package com.degree.back.posture.corrector;

import lombok.Getter;

public class BpcException extends RuntimeException {

  @Getter
  private ErrorCode errorCode;

  public BpcException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}
