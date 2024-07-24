package com.example.farmcollector.config;

import com.example.farmcollector.exception.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }
//
//    // Catch Spring validations
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException e) {
//        List<String> errors = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .toList();
//
//        ApiError apiError = new ApiError("Validation Error", errors);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
//    }
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }
//
//    // To catch & display custom message instead of long debug messages
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Map<String, String>> handleTypeMismatchException(HttpMessageNotReadableException e) {
//        Map<String, String> errors = new HashMap<>();
//        errors.put("details", e.getMessage());
//        errors.put("message", "Invalid input type");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }
//    @ExceptionHandler(FeignException.class)
//    public  ResponseEntity<String> handleFeignClientException(FeignException e){
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(WeatherException.class)
    public ResponseEntity<Object> handleIOException(WeatherException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<Object> handleCityNotFoundException(CityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(WeatherServiceUnavailableException.class)
    public ResponseEntity<Object> handleWeatherServiceUnavailableException(WeatherServiceUnavailableException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
    @ExceptionHandler(UnautharizedRequestException.class)
    public ResponseEntity<Object> handleUnautharizedRequestException(UnautharizedRequestException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

   @ExceptionHandler(CropValidationException.class)
    public ResponseEntity<Object> handleCropValidationException(CropValidationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
   }

   @ExceptionHandler(FarmValidationException.class)
    public ResponseEntity<Object> handleFarmValidationException(FarmValidationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
   }

   @ExceptionHandler(FarmerValidationException.class)
    public ResponseEntity<Object> handleFarmerValidationException(FarmerValidationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
   }

}