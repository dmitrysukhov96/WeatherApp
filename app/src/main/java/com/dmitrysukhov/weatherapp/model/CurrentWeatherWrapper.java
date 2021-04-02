package com.dmitrysukhov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherWrapper {

    @SerializedName("WeatherText")
    private String weatherText;

    @SerializedName("Temperature")
    private Temperature temperature;

    public String getWeatherText() {
        return weatherText;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public class Temperature {
        public Metric getMetric() {
            return metric;
        }

        @SerializedName("Metric")
        Metric metric;
    }

    public class Metric {
        public double getValue() {
            return value;
        }

        @SerializedName("Value")
        double value;
    }
}
