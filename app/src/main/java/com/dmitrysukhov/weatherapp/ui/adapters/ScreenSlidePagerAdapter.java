package com.dmitrysukhov.weatherapp.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dmitrysukhov.weatherapp.ui.WeatherDetailsFragment;
import com.dmitrysukhov.weatherapp.ui.WeatherMainFragment;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;
    private final WeatherMainFragment weatherMainFragment;
    private final WeatherDetailsFragment weatherDetailsFragment;

    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
        weatherMainFragment = new WeatherMainFragment();
        weatherDetailsFragment = new WeatherDetailsFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? weatherMainFragment : weatherDetailsFragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}