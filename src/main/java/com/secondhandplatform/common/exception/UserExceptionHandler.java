package com.secondhandplatform.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> duplicateExceptionHandler(DuplicateException e) {
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<?> mailSendExceptionHandler(MailSendException e) {
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return ResponseEntity.status(500)
                .body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<?> badRequestExceptionHandler(BadRequestException e) {
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return ResponseEntity.status(400)
                .body(errorResult);
    }
}
