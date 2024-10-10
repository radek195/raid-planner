package com.example.raid_planner.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IncorrectParametersException.class)
    public ResponseEntity<Object> handleException(IncorrectParametersException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(EventNotReadyException.class)
    public ResponseEntity<Object> handleException(EventNotReadyException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Object> handleException(EventNotFoundException exception) {
        HttpStatus badRequest = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

}