package com.example.farmcollector.adapter;

import com.example.farmcollector.config.WeatherApiConfig;
import com.example.farmcollector.exception.WeatherException;
import com.example.farmcollector.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class WeatherServiceAdapter {
    private final WeatherApiConfig weatherApiConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CompletableFuture<WeatherData> fetchWeatherData(String cityName) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        return client.prepare("GET", weatherApiConfig.getApiUrl() + "?city_name=" + cityName).setHeader("x-rapidapi-key", weatherApiConfig.getApiKey()).setHeader("x-rapidapi-host", weatherApiConfig.getApiHost()).execute().toCompletableFuture().thenApply(response -> {
            try {
                return parseWeatherData(response.getResponseBody());
            } catch (IOException e) {
                throw new WeatherException("Weather service is unavailable");
            }
        }).whenComplete((result, exception) -> {
            try {
                client.close();
            } catch (IOException e) {
                throw new WeatherException("Weather service is unavailable");            }
        });
    }


    private WeatherData parseWeatherData(String responseBody) throws IOException {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            WeatherData weatherData = new WeatherData();

            weatherData.setName(rootNode.path("name").asText());
            weatherData.setTempMin(rootNode.path("main").path("temp_min").asDouble());
            weatherData.setTempMax(rootNode.path("main").path("temp_max").asDouble());
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
