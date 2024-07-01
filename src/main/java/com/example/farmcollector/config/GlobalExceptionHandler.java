package com.example.farmcollector.config;

import com.example.farmcollector.exception.DuplicateDataException;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<Object> handleDuplicateDataException(DuplicateDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(FarmDataNotFoundException.class)
    public ResponseEntity<Object> handleFarmDataNotFoundException(FarmDataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
