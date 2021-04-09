package com.dmitrysukhov.weatherapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dmitrysukhov.weatherapp.model.wrappers.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.FiveDaysWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.TwelveHoursWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.WeatherRepository;

public class MainViewModel extends ViewModel {
    private final WeatherRepository weatherRepository;

    public MainViewModel() {
        weatherRepository = new WeatherRepository();
    }

    public LiveData<String> getLocationKey(Context context) {
        return weatherRepository.getLocationKey();
    }

    public LiveData<CurrentWeatherWrapper> getCurrentWeatherLiveData() {
        return weatherRepository.getCurrentWeatherLiveData();
    }

    public String getLocalizedName() {
        return weatherRepository.getCurrentCity();
    }

    public LiveData<TwelveHoursWeatherWrapper[]> getTwelveHoursWeatherLiveData() {
        return weatherRepository.getTwelveHoursWeatherLiveData();
    }

    public LiveData<FiveDaysWeatherWrapper> getFiveDaysWeatherLiveData() {
        return weatherRepository.getFiveDaysWeatherLiveData();
    }


    public void getData(String locationKey) {
        weatherRepository.getRecentData(locationKey);
    }

    public void getNewData(Context context) {
        weatherRepository.getNewData(context);
    }

    public void sendThisLifecycle(Lifecycle lifecycle) {
        weatherRepository.setThisLifecycle(lifecycle);
    }
}
