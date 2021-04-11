package com.dmitrysukhov.weatherapp.model.wrappers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FiveDaysWeatherWrapper {

    @SerializedName("DailyForecasts")
    private List<DailyForecast> dailyForecasts;

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public static class DailyForecast {
        @SerializedName("Temperature")
        private Temperature temperature;

        @SerializedName("Day")
        private Day day;

        public Day getDay() {
            return day;
        }

        public Temperature getTemperature() {
            return temperature;
        }
    }


    public static class Day {
        @SerializedName("Icon")
        private int iconNumber;

        @SerializedName("IconPhrase")
        private String iconPhrase;

        public int getIconNumber() {
            return iconNumber;
        }

        public String getIconPhrase() {
            return iconPhrase;
        }
    }

    public static class Temperature {
        @SerializedName("Minimum")
        private Minimum minimum;
        @SerializedName("Maximum")
        private Maximum maximum;

        public Minimum getMinimum() {
            return minimum;
        }

        public Maximum getMaximum() {
            return maximum;
        }
    }

    public static class Minimum {
        @SerializedName("Value")
        private double minValue;

        public double getMinValue() {
            return minValue;
        }
    }

    public static class Maximum {
        @SerializedName("Value")
        private double maxValue;

        public double getMaxValue() {
            return maxValue;
        }
    }
}
