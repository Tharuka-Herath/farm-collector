package com.example.farmcollector.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private String name;
    private double tempMin;
    private double tempMax;
    private int pressure;
    private int humidity;
    private String main;
    private String description;
}
