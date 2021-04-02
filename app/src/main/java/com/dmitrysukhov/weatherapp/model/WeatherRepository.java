package com.dmitrysukhov.weatherapp.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dmitrysukhov.weatherapp.BuildConfig;
import com.dmitrysukhov.weatherapp.network.ApiInterface;
import com.dmitrysukhov.weatherapp.network.RetrofitInstance;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private final MutableLiveData<String> locationKeyLiveData;
    private final MutableLiveData<CurrentWeatherWrapper> currentWeatherLiveData;
    private final MutableLiveData<TwelveHoursWeatherWrapper> twelveHoursWeatherLiveData;
    private final MutableLiveData<FiveDaysWeatherWrapper> fiveDaysWeatherLiveData;
    private String locationKey;
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

    public LiveData<String> getLocationKey(Context context) {
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
                    public void onResponse(Call<CitySearchWrapper[]> call, Response<CitySearchWrapper[]> response) {
                        currentCity = response.body()[0].getLocalizedName();
                        locationKey = response.body()[0].getKeyOfOurCity();  //325343
                        locationKeyLiveData.setValue(locationKey);
                    }

                    @Override
                    public void onFailure(Call<CitySearchWrapper[]> call, Throwable t) {
                        Log.i(BuildConfig.LOG_TAG, t.toString());
                    }
                });
            }
        });
        return locationKeyLiveData;
    }

    public MutableLiveData<CurrentWeatherWrapper> getCurrentWeatherLiveData(String newLocationKey) {
        if (newLocationKey != null) {
            ApiInterface apiInterface = RetrofitInstance.getApiService();

            Call<CurrentWeatherWrapper[]> currentWeatherWrapperCall = apiInterface.getCurrentWeatherData
                    (newLocationKey, BuildConfig.API_KEY, BuildConfig.LANGUAGE, false);
            currentWeatherWrapperCall.enqueue(new Callback<CurrentWeatherWrapper[]>() {
                @Override
                public void onResponse(Call<CurrentWeatherWrapper[]> call, Response<CurrentWeatherWrapper[]> response) {
                    CurrentWeatherWrapper currentWeatherWrapper = response.body()[0];
                    if (currentWeatherWrapper != null) {
                        currentWeatherLiveData.setValue(currentWeatherWrapper);
                    }
                }
                @Override
                public void onFailure(Call<CurrentWeatherWrapper[]> call, Throwable t) {
                    Log.i(BuildConfig.LOG_TAG, t.toString());
                }
            });
        }
        return currentWeatherLiveData;
    }

    public MutableLiveData<TwelveHoursWeatherWrapper> getTwelveHoursWeatherLiveData(String newLocationKey) {
        if (newLocationKey != null) {
            ApiInterface apiInterface = RetrofitInstance.getApiService();

            Call<TwelveHoursWeatherWrapper[]> twelveHoursWeatherWrapperCall = apiInterface.getTwelveHoursWeatherData
                    (newLocationKey, BuildConfig.API_KEY, BuildConfig.LANGUAGE, false,true);
            twelveHoursWeatherWrapperCall.enqueue(new Callback<TwelveHoursWeatherWrapper[]>() {
                @Override
                public void onResponse(Call<TwelveHoursWeatherWrapper[]> call, Response<TwelveHoursWeatherWrapper[]> response) {
                    TwelveHoursWeatherWrapper twelveHoursWeatherWrapper = response.body()[0];
                    if (twelveHoursWeatherWrapper != null) {
                        twelveHoursWeatherLiveData.setValue(twelveHoursWeatherWrapper);
                    }
                }
                @Override
                public void onFailure(Call<TwelveHoursWeatherWrapper[]> call, Throwable t) {
                    Log.i(BuildConfig.LOG_TAG, t.toString());
                }
            });
        }
        return twelveHoursWeatherLiveData;
    }

    public MutableLiveData<FiveDaysWeatherWrapper> getFiveDaysWeatherLiveData(String newLocationKey) {
        if (newLocationKey != null) {
            ApiInterface apiInterface = RetrofitInstance.getApiService();

            Call<FiveDaysWeatherWrapper> fiveDaysWeatherData = apiInterface.getFiveDaysWeatherData
                    (newLocationKey, BuildConfig.API_KEY, BuildConfig.LANGUAGE, false,true);
            fiveDaysWeatherData.enqueue(new Callback<FiveDaysWeatherWrapper>() {
                @Override
                public void onResponse(Call<FiveDaysWeatherWrapper> call, Response<FiveDaysWeatherWrapper> response) {
                    FiveDaysWeatherWrapper fiveDaysWeatherWrapper = response.body();
                    if (fiveDaysWeatherWrapper != null) {
                        fiveDaysWeatherLiveData.setValue(fiveDaysWeatherWrapper);
                    }
                }
                @Override
                public void onFailure(Call<FiveDaysWeatherWrapper> call, Throwable t) {
                    Log.i(BuildConfig.LOG_TAG, t.toString());
                }
            });
        }
        return fiveDaysWeatherLiveData;
    }

}
