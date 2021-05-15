package com.degree.back.posture.corrector.api;

import com.degree.back.posture.corrector.BpcException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BpcException.class)
  public ResponseEntity<Object> handleCityNotFoundException(
      BpcException ex, HttpServletRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "City not found");

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

}
