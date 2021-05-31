package com.udacity.jwdnd.course1.cloudstorage.exception;

import com.udacity.jwdnd.course1.cloudstorage.model.ResultFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class MaxUploadSizeExceededExceptionAdvice {
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public String handleMaxUploadSizeExceededException(
      MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute(
        ResultFactory.createErrorResult(
            "File too large to upload. Select a file smaller than 10MB."));
    return "redirect:/result";
  }
}
