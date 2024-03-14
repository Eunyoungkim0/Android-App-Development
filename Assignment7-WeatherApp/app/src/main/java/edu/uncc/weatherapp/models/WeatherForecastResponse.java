package edu.uncc.weatherapp.models;

import java.util.ArrayList;

public class WeatherForecastResponse {
    public Properties properties;

    public static class Properties {
        public String forecast;
        public ArrayList<Forecast> periods;
    }
}