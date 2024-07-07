package com.example.farmcollector.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {

    private String name;
    private String tempMin;
    private String tempMax;
    private String tempFeelsLike;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String description;
}
