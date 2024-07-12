package com.example.farmcollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {

    private String cityName;
    private String temp;
    private String tempFeelsLike;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String description;
}
