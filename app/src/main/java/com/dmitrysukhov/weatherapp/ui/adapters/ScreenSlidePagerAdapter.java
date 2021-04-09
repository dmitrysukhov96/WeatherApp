package com.dmitrysukhov.weatherapp.ui.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dmitrysukhov.weatherapp.BuildConfig;
import com.dmitrysukhov.weatherapp.ui.WeatherDetailsFragment;
import com.dmitrysukhov.weatherapp.ui.WeatherMainFragment;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;
    private WeatherMainFragment weatherMainFragment;
    private WeatherDetailsFragment weatherDetailsFragment;

    public WeatherMainFragment getWeatherMainFragment() {
        return weatherMainFragment;
    }

    public WeatherDetailsFragment getWeatherDetailsFragment() {
        return weatherDetailsFragment;
    }

    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
        Log.i(BuildConfig.LOG_TAG, "SSPA constructor");
        weatherMainFragment = new WeatherMainFragment();
        weatherDetailsFragment = new WeatherDetailsFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.i(BuildConfig.LOG_TAG, "SSPA createFragment");
        return position==0 ? weatherMainFragment: weatherDetailsFragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}