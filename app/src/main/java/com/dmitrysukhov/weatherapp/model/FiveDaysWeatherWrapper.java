package com.dmitrysukhov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class FiveDaysWeatherWrapper {

    @SerializedName("Daily Forecasts")
    private DailyForecast[] dailyForecasts;

    public DailyForecast[] getDailyForecasts() {
        return dailyForecasts;
    }

    private class DailyForecast {
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


    private class Day {
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

    private class Temperature {
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

    private class Minimum {
        @SerializedName("Value")
        private double minValue;

        public double getMinValue() {
            return minValue;
        }
    }

    private class Maximum {
        @SerializedName("Value")
        private double maxValue;

        public double getMaxValue() {
            return maxValue;
        }
    }
}
