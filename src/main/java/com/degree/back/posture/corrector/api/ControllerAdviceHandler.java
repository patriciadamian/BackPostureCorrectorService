package com.degree.back.posture.corrector.api;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.degree.back.posture.corrector.BpcException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BpcException.class)
  public ResponseEntity<Object> handle(
      BpcException ex, HttpServletRequest request) {

    switch (ex.getErrorCode()) {
      case ENTITY_NOT_FOUND:
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
      case GENERIC_ERROR_CODE:
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
      case LOGIN_ERROR:
        return new ResponseEntity<>(ex.getMessage(), FORBIDDEN);
      default:
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleCityNotFoundException(
      DataIntegrityViolationException ex, HttpServletRequest request) {

    return new ResponseEntity<>("Email must be unique!", INTERNAL_SERVER_ERROR);
  }

}
