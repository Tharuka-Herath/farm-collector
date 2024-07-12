package com.example.farmcollector.api.controller;

import com.example.farmcollector.adapter.WeatherApiResponse;
import com.example.farmcollector.dto.WeatherDataDTO;
import com.example.farmcollector.adapter.WeatherAdapter;
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
    public WeatherApiResponse getWeather(@RequestParam String cityName) throws IOException {
        return weatherAdapter.getWeather(cityName);
    }
}
