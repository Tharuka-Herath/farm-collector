package com.example.farmcollector.service.weather;

import com.example.farmcollector.api.client.WeatherClient;
import com.example.farmcollector.config.WeatherApiConfig;
import com.example.farmcollector.exception.WeatherException;
import com.example.farmcollector.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WeatherAdapterImpl implements WeatherAdapter {
    private final WeatherClient weatherClient;
    private final WeatherApiConfig weatherApiConfig;

    @Override
    public WeatherData getWeather(String cityName) throws IOException {
        String weatherJson = weatherClient.getWeatherByCityName(cityName, weatherApiConfig.getApiKey()); // Replace apiKey with your actual API key
        // Convert JSON to WeatherData
        return convertJsonToWeatherData(weatherJson);
    }

    private WeatherData convertJsonToWeatherData(String weatherJson) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(weatherJson);
            WeatherData weatherData = new WeatherData();

            weatherData.setName(rootNode.path("name").asText());
            weatherData.setTempMin(String.format("%.2f", rootNode.path("main").path("temp_min").asDouble() - 273.15) + " °C");
            weatherData.setTempMax(String.format("%.2f", rootNode.path("main").path("temp_max").asDouble() - 273.15) + " °C");
            weatherData.setTempFeelsLike(String.format("%.2f", rootNode.path("main").path("feels_like").asDouble() - 273.15) + " °C");
            weatherData.setPressure(rootNode.path("main").path("pressure").asInt() + " hPa");
            weatherData.setHumidity(rootNode.path("main").path("humidity").asInt() + " %");
            weatherData.setWindSpeed(rootNode.path("wind").path("speed").asDouble() + " mph");
            JsonNode weatherNode = rootNode.path("weather").get(0);
            weatherData.setDescription(weatherNode.path("description").asText());

            return weatherData;
        } catch (NullPointerException e) {
            throw new WeatherException("Weather data not found");
        }
    }
}
