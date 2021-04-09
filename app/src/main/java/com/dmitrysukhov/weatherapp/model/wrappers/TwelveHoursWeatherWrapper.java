package com.dmitrysukhov.weatherapp.model.wrappers;

import com.google.gson.annotations.SerializedName;

public class TwelveHoursWeatherWrapper {
    @SerializedName("WeatherIcon")
    private int weatherIcon;

    @SerializedName("IconPhrase")
    private String iconPhrase;

    @SerializedName("Temperature")
    private Temperature temperature;

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public static class Temperature {
        @SerializedName("Value")
        private double value;

        public double getValue() {
            return value;
        }
    }
}