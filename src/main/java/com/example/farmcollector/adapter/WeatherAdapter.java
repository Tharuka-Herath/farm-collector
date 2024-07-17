package com.example.farmcollector.adapter;

import com.example.farmcollector.dto.WeatherDataDTO;
import feign.FeignException;

import java.io.IOException;

public interface WeatherAdapter {
    WeatherApiResponse getWeather(String cityName) ;
}
