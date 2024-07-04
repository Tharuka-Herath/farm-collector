package com.example.farmcollector.api.controller;

import com.example.farmcollector.adapter.WeatherServiceAdapter;
import com.example.farmcollector.model.WeatherData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class WeatherController {
    private final WeatherServiceAdapter weatherServiceAdapter;

    public WeatherController(WeatherServiceAdapter weatherServiceAdapter) {
        this.weatherServiceAdapter = weatherServiceAdapter;
    }

    @GetMapping("/weather")
    public CompletableFuture<WeatherData> getWeather(@RequestParam String city) {
        return weatherServiceAdapter.fetchWeatherData(city);
    }
}
