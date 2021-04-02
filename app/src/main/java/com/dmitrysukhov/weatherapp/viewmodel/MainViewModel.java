package com.dmitrysukhov.weatherapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dmitrysukhov.weatherapp.model.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.FiveDaysWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.TwelveHoursWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.WeatherRepository;

public class MainViewModel extends ViewModel {
    private final WeatherRepository weatherRepository;

    public MainViewModel() {
        weatherRepository = new WeatherRepository();
    }

    public LiveData<String> getLocationKey(Context context) {
        return weatherRepository.getLocationKey(context);
    }

    public LiveData<CurrentWeatherWrapper> getCurrentWeatherLiveData(String newLocationKey) {
        return weatherRepository.getCurrentWeatherLiveData(newLocationKey);
    }

    public String getLocalizedName() {
        return weatherRepository.getCurrentCity();
    }

    public LiveData<TwelveHoursWeatherWrapper> getTwelveHoursWeatherLiveData(String newLocationKey) {
        return weatherRepository.getTwelveHoursWeatherLiveData(newLocationKey);
    }

    public LiveData<FiveDaysWeatherWrapper> getFiveDaysWeatherLiveData(String newLocationKey) {
        return weatherRepository.getFiveDaysWeatherLiveData(newLocationKey);
    }

//    public MutableLiveData<OneDayWeatherWrapper> getOneDayWeatherLiveData() {
//        return weatherRepository.getOneDayWeatherLiveData();
//    }
}
