package com.example.farmcollector.exception;

public class CropValidationException extends RuntimeException {
    public CropValidationException(String message){
        super(message);
    }
}
