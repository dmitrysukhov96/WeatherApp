package com.dmitrysukhov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class CitySearchWrapper {
    public String getKeyOfOurCity() {
        return keyOfOurCity;
    }

    @SerializedName("Key")
    private String keyOfOurCity;

    @SerializedName("LocalizedName")
    private String localizedName;

    public String getLocalizedName() {
        return localizedName;
    }
}
