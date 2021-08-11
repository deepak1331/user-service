package com.learn.userservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFounException(ResourceNotFoundException exception, WebRequest request) {

        GlobalError error = new GlobalError(exception.getMessage(), exception.getClass().toString(),
                new Date(), request.getDescription(false), HttpStatus.NOT_FOUND);
        log.error(error.getErrorMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {

        GlobalError error = new GlobalError(exception.getMessage(), exception.getClass().toString(),
                new Date(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(error.getErrorMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler({ArrayIndexOutOfBoundsException.class, IndexOutOfBoundsException.class})
    public ResponseEntity<?> handleIndexOutOfBoundsException(IndexOutOfBoundsException exception, WebRequest request) {
        GlobalError error = new GlobalError(exception.getMessage(), exception.getClass().toString(), new Date(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(error.getErrorMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }
}
