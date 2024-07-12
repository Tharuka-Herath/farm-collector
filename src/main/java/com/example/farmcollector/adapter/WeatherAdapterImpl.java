package com.example.farmcollector.adapter;

import com.example.farmcollector.dto.WeatherDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WeatherAdapterImpl implements WeatherAdapter {
    private final WeatherClient weatherClient;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.host}")
    private String apiHost;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Override
    public WeatherApiResponse getWeather(String cityName) throws IOException {
        return weatherClient.getWeatherByCityName(cityName, apiKey);
    }
}
