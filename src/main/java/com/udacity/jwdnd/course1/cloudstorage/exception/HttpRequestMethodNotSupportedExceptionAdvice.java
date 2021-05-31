package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpRequestMethodNotSupportedExceptionAdvice {
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public String handleHttpRequestMethodNotSupportedException() {
    return "error/404";
  }
}
