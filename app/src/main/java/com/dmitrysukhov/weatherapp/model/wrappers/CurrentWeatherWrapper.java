package com.dmitrysukhov.weatherapp.model.wrappers;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherWrapper {

    @SerializedName("WeatherText")
    private String weatherText;

    @SerializedName("Temperature")
    private Temperature temperature;

    @SerializedName("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;

    @SerializedName("RelativeHumidity")
    private int relativeHumidity;

    @SerializedName("Precip1hr")
    private PrecipOneHour precipOneHour;

    @SerializedName("Pressure")
    private Pressure pressure;

    @SerializedName("Wind")
    private Wind wind;

    @SerializedName("UVIndex")
    private int uVIndex;

    @SerializedName("CloudCover")
    private int cloudCover;

    public int getCloudCover() {
        return cloudCover;
    }

    public int getUVIndex() {
        return uVIndex;
    }

    public Wind getWind() {
        return wind;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public PrecipOneHour getPrecipOneHour() {
        return precipOneHour;
    }

    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public static class Temperature {
        public MetricTemperature getMetricTemperature() {
            return metricTemperature;
        }

        @SerializedName("Metric")
        private MetricTemperature metricTemperature;
    }

    public static class MetricTemperature {
        public double getValueOfMetricTemperature() {
            return valueOfMetricTemperature;
        }

        @SerializedName("Value")
        private double valueOfMetricTemperature;
    }

    public static class RealFeelTemperature {
        @SerializedName("Metric")
        private MetricRealFeelTemperature metricRealFeelTemperature;

        public MetricRealFeelTemperature getMetricRealFeelTemperature() {
            return metricRealFeelTemperature;
        }
    }

    public static class MetricRealFeelTemperature {
        @SerializedName("Value")
        private double valueRealFeel;

        public double getValueRealFeel() {
            return valueRealFeel;
        }
    }

    public static class PrecipOneHour {
        @SerializedName("Metric")
        private MetricPrecip metricPrecip;

        public MetricPrecip getMetricPrecip() {
            return metricPrecip;
        }
    }

    public static class MetricPrecip {
        @SerializedName("Value")
        private int valuePrecip;

        @SerializedName("Unit")
        private String unit;

        public String getValueAndUnitPrecip() {
            return valuePrecip + " " + unit;
        }
    }

    public class Pressure {
        @SerializedName("Metric")
        private MetricPressure metricPressure;

        public MetricPressure getMetricPressure() {
            return metricPressure;
        }
    }

    public static class MetricPressure {
        @SerializedName("Value")
        private int valuePressure;

        @SerializedName("Unit")
        private String unitPressure;

        public String getValueAndUnitPressure() {
            return valuePressure + " " + unitPressure;
        }
    }

    public static class Wind {
        @SerializedName("Speed")
        private SpeedOfWind speedOfWind;

        public SpeedOfWind getSpeedOfWind() {
            return speedOfWind;
        }
    }

    public static class SpeedOfWind {
        @SerializedName("Metric")
        private MetricSpeedOfWind metricSpeedOfWind;

        public MetricSpeedOfWind getMetricSpeedOfWind() {
            return metricSpeedOfWind;
        }
    }

    public static class MetricSpeedOfWind {
        @SerializedName("Value")
        private int speedOfWindValue;
        @SerializedName("Unit")
        private String unitSpeedOfWind;

        public String getValueAndUnitSpeedOfWind() {
            return speedOfWindValue + " " + unitSpeedOfWind;
        }
    }


}
