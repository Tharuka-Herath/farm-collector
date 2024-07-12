package com.example.farmcollector.api.response;

import com.example.farmcollector.dto.WeatherDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponseWeatherData {

    private String farmId;
    private String farmName;
    private String location;
    private Double farmArea;
    private WeatherDataDTO weatherData;
}
