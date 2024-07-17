package com.example.farmcollector.exception;

public class WeatherServiceUnavailableException extends RuntimeException{
    public WeatherServiceUnavailableException(String message){
        super(message);
    }
}
