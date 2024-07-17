package com.example.farmcollector.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String message){
        super(message);
    }
}
