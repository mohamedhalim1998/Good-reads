package com.mohamed.halim.goodreads.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerImpl  extends ResponseEntityExceptionHandler {
    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "No Such User Found")
    @ExceptionHandler({UserNotFoundException.class, AuthorNoFoundException.class, AuthorNoFoundException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
