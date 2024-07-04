package com.example.farmcollector.adapter;

import com.example.farmcollector.config.WeatherApiConfig;
import com.example.farmcollector.exception.WeatherException;
import com.example.farmcollector.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class WeatherServiceAdapter {
    private final WeatherApiConfig weatherApiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CompletableFuture<WeatherData> fetchWeatherData(String cityName) {
        return CompletableFuture.supplyAsync(() -> {
            String url = weatherApiConfig.getApiUrl() + "?city_name=" + cityName;

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-key", weatherApiConfig.getApiKey());
            headers.set("x-rapidapi-host", weatherApiConfig.getApiHost());

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            try {
                return parseWeatherData(response.getBody());
            } catch (IOException e) {
                throw new WeatherException("Weather service is unavailable");
            }
        });
    }


    private WeatherData parseWeatherData(String responseBody) throws IOException {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            WeatherData weatherData = new WeatherData();

            weatherData.setName(rootNode.path("name").asText());
            weatherData.setTempMin(rootNode.path("main").path("temp_min").asDouble()-273.15);
            weatherData.setTempMax(rootNode.path("main").path("temp_max").asDouble()-273.15);
            weatherData.setPressure(rootNode.path("main").path("pressure").asInt());
            weatherData.setHumidity(rootNode.path("main").path("humidity").asInt());

            JsonNode weatherNode = rootNode.path("weather").get(0);

            weatherData.setMain(weatherNode.path("main").asText());

            weatherData.setDescription(weatherNode.path("description").asText());

            return weatherData;
        } catch (NullPointerException e) {
            throw new WeatherException("Weather data not found");
        }
    }
}
