package com.dmitrysukhov.weatherapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmitrysukhov.weatherapp.ui.adapters.RecyclerAdapterMain;
import com.dmitrysukhov.weatherapp.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherMainFragment extends Fragment{

    private SharedPreferences mySharedPrefs;
    private RecyclerAdapterMain recyclerAdapterMain;
    private TextView textViewCurrentTemperature;
    private TextView textViewCurrentWeather;
    private TextView textViewCurrentCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySharedPrefs = getActivity().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
        String dateTimeUpdated = getString(R.string.updated)+" "+ DateFormat.getDateTimeInstance().format(new Date());
        ((TextView)view.findViewById(R.id.textView_main_updated)).setText(dateTimeUpdated);
        RecyclerView recyclerViewMain = view.findViewById(R.id.recycler_view_main_fragment_recent_days);
        String[] stringArrayMainDays = getResources().getStringArray(R.array.day_main);
        String[] stringArrayMainWeather = {
                mySharedPrefs.getString(getString(R.string.today_weather_phrase),getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.tomorrow_weather_phrase),getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.day_after_tomorrow_weather_phrase),getString(R.string.nothing))};
        String[] stringArrayMainTemperature = {
                mySharedPrefs.getString(getString(R.string.today_temperature_minmax), getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.tomorrow_temperature_minmax), getString(R.string.nothing)),
                mySharedPrefs.getString(getString(R.string.day_after_tomorrow_temperature_minmax), getString(R.string.nothing))};
        int[] intArrayMainIconNumber = {
                mySharedPrefs.getInt(getString(R.string.today_icon), 0),
                mySharedPrefs.getInt(getString(R.string.tomorrow_icon), 0),
                mySharedPrefs.getInt(getString(R.string.day_after_tomorrow_icon), 0)};
        recyclerAdapterMain = new RecyclerAdapterMain(requireContext(), stringArrayMainDays, stringArrayMainWeather, stringArrayMainTemperature, intArrayMainIconNumber);
        recyclerViewMain.setAdapter(recyclerAdapterMain);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(requireContext()));
        textViewCurrentCity = view.findViewById(R.id.textView_main_fragment_city);
        textViewCurrentTemperature = view.findViewById(R.id.textView_main_fragment_temperature);
        textViewCurrentWeather = view.findViewById(R.id.textView_main_fragment_state);
        setRecentData();
        LinearLayout linearLayoutMainMore = view.findViewById(R.id.linearLayout_main_fragment_more_details);
        linearLayoutMainMore.setOnClickListener(view1 -> {
            ((ViewPager2)requireActivity().findViewById(R.id.view_pager_main_activity_container)).setCurrentItem(1,true);
        });
    }

    public void setRecentData() {
        //это не работает если вызывать из МэйнАктивити, пишет что Fragment not attached to a context
        textViewCurrentCity.setText(mySharedPrefs.getString(getString(R.string.current_city), getString(R.string.your_city)));
        textViewCurrentTemperature.setText(mySharedPrefs.getString(getString(R.string.current_temperature), getString(R.string.no_temperature)));
        textViewCurrentWeather.setText(mySharedPrefs.getString(getString(R.string.current_weather), getString(R.string.swipe_to_refresh)));
        recyclerAdapterMain.notifyDataSetChanged();
    }
}