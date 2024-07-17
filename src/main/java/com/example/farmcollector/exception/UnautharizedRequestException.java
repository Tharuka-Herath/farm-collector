package com.example.farmcollector.exception;

public class UnautharizedRequestException extends RuntimeException {
    public UnautharizedRequestException(String message){
        super(message);
    }
}
