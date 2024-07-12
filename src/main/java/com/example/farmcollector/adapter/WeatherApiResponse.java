package com.example.farmcollector.adapter;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherApiResponse {
    private String name;
    private Main main;
    private List<Weather> weather;
    private Wind wind;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private double temp;
        private double feels_like;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Weather {
        private String main;
        private String description;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wind {
        private double speed;
        private String deg;
        private String gust;
    }
}
