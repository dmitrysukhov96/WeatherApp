package com.dmitrysukhov.weatherapp.network;

import com.dmitrysukhov.weatherapp.model.CitySearchWrapper;
import com.dmitrysukhov.weatherapp.model.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.FiveDaysWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.TwelveHoursWeatherWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("./locations/v1/cities/search")
    Call<CitySearchWrapper[]> getLocationKey(@Query("apikey") String apiKey, @Query("q") String latitudeAndLongitude);

    @GET("./currentconditions/v1/{locationKey}")
    Call<CurrentWeatherWrapper[]> getCurrentWeatherData(@Path("locationKey") String locationKey,
                                                        @Query("apikey") String apiKey,
                                                        @Query("language") String language,
                                                        @Query("details") boolean details);

    @GET("./forecasts/v1/hourly/12hour/{locationKey}")
    Call<TwelveHoursWeatherWrapper[]> getTwelveHoursWeatherData(@Path("locationKey") String locationKey,
                                                              @Query("apikey") String apiKey,
                                                              @Query("language") String language,
                                                              @Query("details") boolean details,
                                                              @Query("metric") boolean metric);

    @GET("./forecasts/v1/daily/5day/{locationKey}")
    Call<FiveDaysWeatherWrapper> getFiveDaysWeatherData(@Path("locationKey") String locationKey,
                                                        @Query("apikey") String apiKey,
                                                        @Query("language") String language,
                                                        @Query("details") boolean details,
                                                        @Query("metric") boolean metric);
}
