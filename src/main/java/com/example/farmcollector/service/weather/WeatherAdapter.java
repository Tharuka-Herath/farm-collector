package com.example.farmcollector.service.weather;

import com.example.farmcollector.model.WeatherData;

import java.io.IOException;

public interface WeatherAdapter {
    WeatherData getWeather(String cityName) throws IOException;
}
