package com.dmitrysukhov.weatherapp.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dmitrysukhov.weatherapp.BuildConfig;
import com.dmitrysukhov.weatherapp.model.wrappers.CitySearchWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.FiveDaysWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.TwelveHoursWeatherWrapper;
import com.dmitrysukhov.weatherapp.network.ApiInterface;
import com.dmitrysukhov.weatherapp.network.RetrofitInstance;
import com.dmitrysukhov.weatherapp.ui.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private final MutableLiveData<String> locationKeyLiveData;
    private final MutableLiveData<CurrentWeatherWrapper> currentWeatherLiveData;
    private final MutableLiveData<TwelveHoursWeatherWrapper[]> twelveHoursWeatherLiveData;
    private final MutableLiveData<FiveDaysWeatherWrapper> fiveDaysWeatherLiveData;
    private String locationKey;  //needed when stub is off
    private String currentCity;

    public String getCurrentCity() {
        return currentCity;
    }

    public WeatherRepository() {
        locationKeyLiveData = new MutableLiveData<>();
        currentWeatherLiveData = new MutableLiveData<>();
        twelveHoursWeatherLiveData = new MutableLiveData<>();
        fiveDaysWeatherLiveData = new MutableLiveData<>();
    }

    public LiveData<String> getLocationKey() {
        return locationKeyLiveData;
    }

    public MutableLiveData<CurrentWeatherWrapper> getCurrentWeatherLiveData() {
        return currentWeatherLiveData;
    }

    public MutableLiveData<TwelveHoursWeatherWrapper[]> getTwelveHoursWeatherLiveData() {
        return twelveHoursWeatherLiveData;
    }

    public MutableLiveData<FiveDaysWeatherWrapper> getFiveDaysWeatherLiveData() {
        return fiveDaysWeatherLiveData;
    }

    public void getRecentData(String newLocationKey) {
        ApiInterface apiInterface = RetrofitInstance.getApiService();
        if (newLocationKey != null) {
            Call<CurrentWeatherWrapper[]> currentWeatherWrapperCall = apiInterface.getCurrentWeatherData
                    (newLocationKey, BuildConfig.API_KEY, BuildConfig.LANGUAGE, true);
            currentWeatherWrapperCall.enqueue(new Callback<CurrentWeatherWrapper[]>() {
                @Override
                public void onResponse(@NonNull Call<CurrentWeatherWrapper[]> call, @NonNull Response<CurrentWeatherWrapper[]> response) {
                    if (response.body() != null) {
                        currentWeatherLiveData.setValue(response.body()[0]);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CurrentWeatherWrapper[]> call, @NonNull Throwable t) {
                    Log.i(BuildConfig.LOG_TAG, t.toString());
                }
            });

            Call<FiveDaysWeatherWrapper> fiveDaysWeatherData = apiInterface.getFiveDaysWeatherData
                    (newLocationKey, BuildConfig.API_KEY, BuildConfig.LANGUAGE, false, true);
            fiveDaysWeatherData.enqueue(new Callback<FiveDaysWeatherWrapper>() {
                @Override
                public void onResponse(@NonNull Call<FiveDaysWeatherWrapper> call, @NonNull Response<FiveDaysWeatherWrapper> response) {
                    if (response.body() != null) {
                        fiveDaysWeatherLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FiveDaysWeatherWrapper> call, @NonNull Throwable t) {
                    Log.i(BuildConfig.LOG_TAG, t.toString());
                }
            });
            Call<TwelveHoursWeatherWrapper[]> twelveHoursWeatherWrapperCall = apiInterface.getTwelveHoursWeatherData
                    (newLocationKey, BuildConfig.API_KEY, BuildConfig.LANGUAGE, false, true);
            twelveHoursWeatherWrapperCall.enqueue(new Callback<TwelveHoursWeatherWrapper[]>() {
                @Override
                public void onResponse(@NonNull Call<TwelveHoursWeatherWrapper[]> call, @NonNull Response<TwelveHoursWeatherWrapper[]> response) {
                    if (response.body() != null) {
                        twelveHoursWeatherLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TwelveHoursWeatherWrapper[]> call, @NonNull Throwable t) {
                    Log.i(BuildConfig.LOG_TAG, t.toString());
                }
            });
            //Old stub
            //String jsonCurrent = "[ { \"LocalObservationDateTime\": \"2021-04-08T16:26:00+03:00\", \"EpochTime\": 1617888360, \"WeatherText\": \"Солнечно\", \"WeatherIcon\": 1, \"HasPrecipitation\": false, \"PrecipitationType\": null, \"IsDayTime\": true, \"Temperature\": { \"Metric\": { \"Value\": 10, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 50, \"Unit\": \"F\", \"UnitType\": 18 } }, \"RealFeelTemperature\": { \"Metric\": { \"Value\": 9.6, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 49, \"Unit\": \"F\", \"UnitType\": 18 } }, \"RealFeelTemperatureShade\": { \"Metric\": { \"Value\": 7.6, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 46, \"Unit\": \"F\", \"UnitType\": 18 } }, \"RelativeHumidity\": 27, \"IndoorRelativeHumidity\": 29, \"DewPoint\": { \"Metric\": { \"Value\": -7.8, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 18, \"Unit\": \"F\", \"UnitType\": 18 } }, \"Wind\": { \"Direction\": { \"Degrees\": 293, \"Localized\": \"ЗСЗ\", \"English\": \"WNW\" }, \"Speed\": { \"Metric\": { \"Value\": 13, \"Unit\": \"km/h\", \"UnitType\": 7 }, \"Imperial\": { \"Value\": 8.1, \"Unit\": \"mi/h\", \"UnitType\": 9 } } }, \"WindGust\": { \"Speed\": { \"Metric\": { \"Value\": 13, \"Unit\": \"km/h\", \"UnitType\": 7 }, \"Imperial\": { \"Value\": 8.1, \"Unit\": \"mi/h\", \"UnitType\": 9 } } }, \"UVIndex\": 2, \"UVIndexText\": \"Низк.\", \"Visibility\": { \"Metric\": { \"Value\": 16.1, \"Unit\": \"km\", \"UnitType\": 6 }, \"Imperial\": { \"Value\": 10, \"Unit\": \"mi\", \"UnitType\": 2 } }, \"ObstructionsToVisibility\": \"\", \"CloudCover\": 0, \"Ceiling\": { \"Metric\": { \"Value\": 4755, \"Unit\": \"m\", \"UnitType\": 5 }, \"Imperial\": { \"Value\": 15600, \"Unit\": \"ft\", \"UnitType\": 0 } }, \"Pressure\": { \"Metric\": { \"Value\": 1017, \"Unit\": \"mb\", \"UnitType\": 14 }, \"Imperial\": { \"Value\": 30.03, \"Unit\": \"inHg\", \"UnitType\": 12 } }, \"PressureTendency\": { \"LocalizedText\": \"Постоянное\", \"Code\": \"S\" }, \"Past24HourTemperatureDeparture\": { \"Metric\": { \"Value\": 5, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 9, \"Unit\": \"F\", \"UnitType\": 18 } }, \"ApparentTemperature\": { \"Metric\": { \"Value\": 10.6, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 51, \"Unit\": \"F\", \"UnitType\": 18 } }, \"WindChillTemperature\": { \"Metric\": { \"Value\": 8.3, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 47, \"Unit\": \"F\", \"UnitType\": 18 } }, \"WetBulbTemperature\": { \"Metric\": { \"Value\": 3.2, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 38, \"Unit\": \"F\", \"UnitType\": 18 } }, \"Precip1hr\": { \"Metric\": { \"Value\": 0, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0, \"Unit\": \"in\", \"UnitType\": 1 } }, \"PrecipitationSummary\": { \"Precipitation\": { \"Metric\": { \"Value\": 0, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0, \"Unit\": \"in\", \"UnitType\": 1 } }, \"PastHour\": { \"Metric\": { \"Value\": 0, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0, \"Unit\": \"in\", \"UnitType\": 1 } }, \"Past3Hours\": { \"Metric\": { \"Value\": 0.5, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0.02, \"Unit\": \"in\", \"UnitType\": 1 } }, \"Past6Hours\": { \"Metric\": { \"Value\": 0.5, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0.02, \"Unit\": \"in\", \"UnitType\": 1 } }, \"Past9Hours\": { \"Metric\": { \"Value\": 0.5, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0.02, \"Unit\": \"in\", \"UnitType\": 1 } }, \"Past12Hours\": { \"Metric\": { \"Value\": 0.5, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0.02, \"Unit\": \"in\", \"UnitType\": 1 } }, \"Past18Hours\": { \"Metric\": { \"Value\": 0.5, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0.02, \"Unit\": \"in\", \"UnitType\": 1 } }, \"Past24Hours\": { \"Metric\": { \"Value\": 1.4, \"Unit\": \"mm\", \"UnitType\": 3 }, \"Imperial\": { \"Value\": 0.06, \"Unit\": \"in\", \"UnitType\": 1 } } }, \"TemperatureSummary\": { \"Past6HourRange\": { \"Minimum\": { \"Metric\": { \"Value\": 7.5, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 45, \"Unit\": \"F\", \"UnitType\": 18 } }, \"Maximum\": { \"Metric\": { \"Value\": 11.1, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 52, \"Unit\": \"F\", \"UnitType\": 18 } } }, \"Past12HourRange\": { \"Minimum\": { \"Metric\": { \"Value\": 1.2, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 34, \"Unit\": \"F\", \"UnitType\": 18 } }, \"Maximum\": { \"Metric\": { \"Value\": 11.1, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 52, \"Unit\": \"F\", \"UnitType\": 18 } } }, \"Past24HourRange\": { \"Minimum\": { \"Metric\": { \"Value\": 1.2, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 34, \"Unit\": \"F\", \"UnitType\": 18 } }, \"Maximum\": { \"Metric\": { \"Value\": 11.1, \"Unit\": \"C\", \"UnitType\": 17 }, \"Imperial\": { \"Value\": 52, \"Unit\": \"F\", \"UnitType\": 18 } } } }, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/current-weather/325343\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/current-weather/325343\" } ]";
//            CurrentWeatherWrapper[] currentWeatherWrapperArray = new Gson().fromJson(jsonCurrent, CurrentWeatherWrapper[].class);
//            if (currentWeatherWrapperArray != null) {
//                currentWeatherLiveData.setValue(currentWeatherWrapperArray[0]);
//            }
//            String jsonFiveDays = "{ \"Headline\": {}, \"DailyForecasts\": [ { \"Temperature\": { \"Minimum\": { \"Value\": 4.6 }, \"Maximum\": { \"Value\": 13.7 } }, \"Day\": { \"Icon\": 4, \"IconPhrase\": \"Переменная облачность\" } }, { \"Date\": \"2021-04-03T07:00:00+03:00\", \"EpochDate\": 1617422400, \"Temperature\": { \"Minimum\": { \"Value\": 3.9, \"Unit\": \"C\", \"UnitType\": 17 }, \"Maximum\": { \"Value\": 12.1, \"Unit\": \"C\", \"UnitType\": 17 } }, \"Day\": { \"Icon\": 12, \"IconPhrase\": \"Ливни\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\" }, \"Night\": { \"Icon\": 18, \"IconPhrase\": \"Дождь\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\" }, \"Sources\": [ \"AccuWeather\" ], \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=2&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=2&unit=c\" }, { \"Date\": \"2021-04-04T07:00:00+03:00\", \"EpochDate\": 1617508800, \"Temperature\": { \"Minimum\": { \"Value\": 3.4, \"Unit\": \"C\", \"UnitType\": 17 }, \"Maximum\": { \"Value\": 9.9, \"Unit\": \"C\", \"UnitType\": 17 } }, \"Day\": { \"Icon\": 12, \"IconPhrase\": \"Ливни\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\" }, \"Night\": { \"Icon\": 12, \"IconPhrase\": \"Ливни\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\" }, \"Sources\": [ \"AccuWeather\" ], \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=3&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=3&unit=c\" }, { \"Date\": \"2021-04-05T07:00:00+03:00\", \"EpochDate\": 1617595200, \"Temperature\": { \"Minimum\": { \"Value\": 4.4, \"Unit\": \"C\", \"UnitType\": 17 }, \"Maximum\": { \"Value\": 10.3, \"Unit\": \"C\", \"UnitType\": 17 } }, \"Day\": { \"Icon\": 12, \"IconPhrase\": \"Ливни\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\" }, \"Night\": { \"Icon\": 33, \"IconPhrase\": \"Ясно\", \"HasPrecipitation\": false }, \"Sources\": [ \"AccuWeather\" ], \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=4&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=4&unit=c\" }, { \"Date\": \"2021-04-06T07:00:00+03:00\", \"EpochDate\": 1617681600, \"Temperature\": { \"Minimum\": { \"Value\": 4.5, \"Unit\": \"C\", \"UnitType\": 17 }, \"Maximum\": { \"Value\": 11.2, \"Unit\": \"C\", \"UnitType\": 17 } }, \"Day\": { \"Icon\": 6, \"IconPhrase\": \"Преимущественно облачно\", \"HasPrecipitation\": false }, \"Night\": { \"Icon\": 36, \"IconPhrase\": \"Переменная облачность\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\" }, \"Sources\": [ \"AccuWeather\" ], \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=5&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/daily-weather-forecast/325343?day=5&unit=c\" } ] }";
//            FiveDaysWeatherWrapper fiveDaysWeatherWrapper = new Gson().fromJson(jsonFiveDays, FiveDaysWeatherWrapper.class);
//            if (fiveDaysWeatherWrapper != null) {
//                fiveDaysWeatherLiveData.setValue(fiveDaysWeatherWrapper);
//            }
//            String jsonTwelveHours = "[ { \"DateTime\": \"2021-04-02T15:00:00+03:00\", \"EpochDateTime\": 1617364800, \"WeatherIcon\": 2, \"IconPhrase\": \"Преимущественно ясно\", \"HasPrecipitation\": false, \"IsDaylight\": true, \"Temperature\": { \"Value\": 13.6, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 7, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=15&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=15&unit=c\" }, { \"DateTime\": \"2021-04-02T16:00:00+03:00\", \"EpochDateTime\": 1617368400, \"WeatherIcon\": 3, \"IconPhrase\": \"Небольшая облачность\", \"HasPrecipitation\": false, \"IsDaylight\": true, \"Temperature\": { \"Value\": 13.6, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 6, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=16&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=16&unit=c\" }, { \"DateTime\": \"2021-04-02T17:00:00+03:00\", \"EpochDateTime\": 1617372000, \"WeatherIcon\": 4, \"IconPhrase\": \"Переменная облачность\", \"HasPrecipitation\": false, \"IsDaylight\": true, \"Temperature\": { \"Value\": 12.9, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 5, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=17&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=17&unit=c\" }, { \"DateTime\": \"2021-04-02T18:00:00+03:00\", \"EpochDateTime\": 1617375600, \"WeatherIcon\": 7, \"IconPhrase\": \"Облачно\", \"HasPrecipitation\": false, \"IsDaylight\": true, \"Temperature\": { \"Value\": 12.4, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 5, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=18&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=18&unit=c\" }, { \"DateTime\": \"2021-04-02T19:00:00+03:00\", \"EpochDateTime\": 1617379200, \"WeatherIcon\": 7, \"IconPhrase\": \"Облачно\", \"HasPrecipitation\": false, \"IsDaylight\": true, \"Temperature\": { \"Value\": 11.7, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 8, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=19&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=19&unit=c\" }, { \"DateTime\": \"2021-04-02T20:00:00+03:00\", \"EpochDateTime\": 1617382800, \"WeatherIcon\": 12, \"IconPhrase\": \"Ливни\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\", \"IsDaylight\": false, \"Temperature\": { \"Value\": 11, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 55, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=20&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=20&unit=c\" }, { \"DateTime\": \"2021-04-02T21:00:00+03:00\", \"EpochDateTime\": 1617386400, \"WeatherIcon\": 12, \"IconPhrase\": \"Ливни\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\", \"IsDaylight\": false, \"Temperature\": { \"Value\": 10, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 55, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=21&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=21&unit=c\" }, { \"DateTime\": \"2021-04-02T22:00:00+03:00\", \"EpochDateTime\": 1617390000, \"WeatherIcon\": 36, \"IconPhrase\": \"Переменная облачность\", \"HasPrecipitation\": false, \"IsDaylight\": false, \"Temperature\": { \"Value\": 9.7, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 49, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=22&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=22&unit=c\" }, { \"DateTime\": \"2021-04-02T23:00:00+03:00\", \"EpochDateTime\": 1617393600, \"WeatherIcon\": 36, \"IconPhrase\": \"Переменная облачность\", \"HasPrecipitation\": false, \"IsDaylight\": false, \"Temperature\": { \"Value\": 8.9, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 17, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=23&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=1&hbhhour=23&unit=c\" }, { \"DateTime\": \"2021-04-03T00:00:00+03:00\", \"EpochDateTime\": 1617397200, \"WeatherIcon\": 36, \"IconPhrase\": \"Переменная облачность\", \"HasPrecipitation\": false, \"IsDaylight\": false, \"Temperature\": { \"Value\": 8.1, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 17, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=2&hbhhour=0&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=2&hbhhour=0&unit=c\" }, { \"DateTime\": \"2021-04-03T01:00:00+03:00\", \"EpochDateTime\": 1617400800, \"WeatherIcon\": 36, \"IconPhrase\": \"Переменная облачность\", \"HasPrecipitation\": false, \"IsDaylight\": false, \"Temperature\": { \"Value\": 7.6, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 22, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=2&hbhhour=1&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=2&hbhhour=1&unit=c\" }, { \"DateTime\": \"2021-04-03T02:00:00+03:00\", \"EpochDateTime\": 1617404400, \"WeatherIcon\": 40, \"IconPhrase\": \"Преимущественно облачно с ливнями\", \"HasPrecipitation\": true, \"PrecipitationType\": \"Rain\", \"PrecipitationIntensity\": \"Light\", \"IsDaylight\": false, \"Temperature\": { \"Value\": 6.9, \"Unit\": \"C\", \"UnitType\": 17 }, \"PrecipitationProbability\": 51, \"MobileLink\": \"http://m.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=2&hbhhour=2&unit=c\", \"Link\": \"http://www.accuweather.com/ru/ua/odesa/325343/hourly-weather-forecast/325343?day=2&hbhhour=2&unit=c\" } ]";
//            TwelveHoursWeatherWrapper[] twelveHoursWeatherWrapper = new Gson().fromJson(jsonTwelveHours, TwelveHoursWeatherWrapper[].class);
//            if (twelveHoursWeatherWrapper != null) {
//                twelveHoursWeatherLiveData.setValue(twelveHoursWeatherWrapper);
//            }
        }
    }

    public void getNewData(Context context) {
        locationKeyLiveData.observe((MainActivity) context, this::getRecentData);
        getNewLocationKey(context);
    }

    private void getNewLocationKey(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location != null) {
                ApiInterface apiInterface = RetrofitInstance.getApiService();
                Call<CitySearchWrapper[]> citySearchWrapperCall = apiInterface.getLocationKey(BuildConfig.API_KEY,
                        location.getLatitude() + ", " + location.getLongitude());
                citySearchWrapperCall.enqueue(new Callback<CitySearchWrapper[]>() {
                    @Override
                    public void onResponse(@NonNull Call<CitySearchWrapper[]> call, @NonNull Response<CitySearchWrapper[]> response) {
                        if (response.body()!=null){
                        currentCity = response.body()[0].getLocalizedName();
                        locationKey = response.body()[0].getKeyOfOurCity();  //325343
                        locationKeyLiveData.setValue(locationKey);
                    }}

                    @Override
                    public void onFailure(@NonNull Call<CitySearchWrapper[]> call, @NonNull Throwable t) {
                        Log.i(BuildConfig.LOG_TAG, t.toString());
                    }
                });
            }
        });
//        currentCity = "Одесса заглушка";
//        locationKeyLiveData.setValue("325343");
    }
}
