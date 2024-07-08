package com.example.farmcollector.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "${weather.api.url}", configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface WeatherClient {

    @GetMapping("/weather")
    String getWeatherByCityName(@RequestParam("city_name") String city, @RequestHeader("X-RapidAPI-Key") String apiKey);
}
