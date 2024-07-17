package com.example.farmcollector.adapter;

import com.example.farmcollector.dto.WeatherDataDTO;
import com.example.farmcollector.exception.*;
import feign.FeignException;
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


    @Override
    public WeatherApiResponse getWeather(String cityName) {

        try {
            WeatherApiResponse weatherApiResponse= weatherClient.getWeatherByCityName(cityName, apiKey);
            return switch (weatherApiResponse.getCod()) {
                case 200 -> weatherApiResponse;
                case 404 -> throw new CityNotFoundException("City not found");
                case 401 -> throw new UnautharizedRequestException("unauthorized Request");
                case 400-> throw new BadRequestException("invalid input");
                case 503 -> throw new WeatherServiceUnavailableException("Weather service is unavailable");
                default -> throw new WeatherException("An error occurred while fetching weather data "+weatherApiResponse.getCod());
            };
        } catch (FeignException e) {
            handleFeignException(e);
            throw new WeatherException("An error occurred while fetching weather data");
        }

    }

    private void handleFeignException(FeignException e) {
        switch (e.status()) {
            case 404:
                throw new CityNotFoundException("City not found");
            case 503:
                throw new WeatherServiceUnavailableException("Weather service is unavailable");
            default:
                throw new WeatherException("An error occurred while fetching weather data: " + e.getMessage()+
                        " check your internet connection or contact API provider");
        }
    }
}
