package com.example.farmcollector.config;

import com.example.farmcollector.exception.ApiError;
import com.example.farmcollector.exception.DuplicateDataException;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.exception.WeatherException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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

    @ExceptionHandler(WeatherException.class)
    public ResponseEntity<Object> handleIOException(WeatherException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiError apiError = new ApiError("Validation Error", errors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}