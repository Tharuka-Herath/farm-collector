package com.example.farmcollector.dto;

import com.example.farmcollector.model.WeatherData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {

    private Long id;
    private String farmId;
    private String farmName;
    private String location;
    private Double farmArea;
    private WeatherData weatherData;
}
