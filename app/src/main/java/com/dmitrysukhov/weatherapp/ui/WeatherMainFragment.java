package com.dmitrysukhov.weatherapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dmitrysukhov.weatherapp.ui.adapters.RecyclerAdapterMain;
import com.dmitrysukhov.weatherapp.R;

public class WeatherMainFragment extends Fragment {

    SharedPreferences mySharedPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySharedPrefs = getContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewMain = view.findViewById(R.id.recycler_view_main_fragment_recent_days);

        String[] stringArrayMainDays = getResources().getStringArray(R.array.day_main);
        String[] stringArrayMainWeather = {mySharedPrefs.getString(getString(R.string.today_weather_phrase),getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.tomorrow_weather_phrase),getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.day_after_tomorrow_weather_phrase),getString(R.string.nothing))};
        String[] stringArrayMainTemperature = {mySharedPrefs.getString(getString(R.string.today_temperature_minmax), getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.tomorrow_temperature_minmax), getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.day_after_tomorrow_temperature_minmax), getString(R.string.nothing))};
        int[] intArrayMainIconNumber = {mySharedPrefs.getInt(getString(R.string.today_icon), 0),
                mySharedPrefs.getInt(getString(R.string.tomorrow_icon), 0),
                mySharedPrefs.getInt(getString(R.string.day_after_tomorrow_icon), 0)};
        RecyclerAdapterMain recyclerAdapterMain = new RecyclerAdapterMain(requireContext(), stringArrayMainDays, stringArrayMainWeather, stringArrayMainTemperature, intArrayMainIconNumber);
        recyclerViewMain.setAdapter(recyclerAdapterMain);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(requireContext()));

//        if (requireContext() instanceof MainFragmentCallback) {
        //mainFragmentCallback = (MainFragmentCallback) requireContext();
//        }

        LinearLayout linearLayoutMainMore = view.findViewById(R.id.linearLayout_main_fragment_more_details);
        linearLayoutMainMore.setOnClickListener(view1 -> {
            //navigateToSecondFragment();
        });
    }
}