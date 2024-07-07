package com.example.farmcollector.api.controller;

import com.example.farmcollector.model.WeatherData;
import com.example.farmcollector.service.weather.WeatherAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class WeatherController {
  private final WeatherAdapter weatherAdapter;


    @GetMapping("/weather")
    public WeatherData getWeather(@RequestParam String cityName) throws IOException {
        return weatherAdapter.getWeather(cityName);
    }
}
