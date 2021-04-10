package com.dmitrysukhov.weatherapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmitrysukhov.weatherapp.BuildConfig;
import com.dmitrysukhov.weatherapp.model.wrappers.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.FiveDaysWeatherWrapper;
import com.dmitrysukhov.weatherapp.ui.adapters.RecyclerAdapterMain;
import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.viewmodel.MainViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherMainFragment extends Fragment {

    private MainViewModel mainViewModel;
    private RecyclerAdapterMain recyclerAdapterMain;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        CurrentWeatherWrapper currentWeatherWrapper = mainViewModel.getCurrentWeatherLiveData().getValue();
        FiveDaysWeatherWrapper fiveDaysWeatherWrapper = mainViewModel.getFiveDaysWeatherLiveData().getValue();
        RecyclerView recyclerViewMain = view.findViewById(R.id.recycler_view_main_fragment_recent_days);
        recyclerAdapterMain = new RecyclerAdapterMain(getActivity(),fiveDaysWeatherWrapper);
        recyclerViewMain.setAdapter(recyclerAdapterMain);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(requireContext()));
        LinearLayout linearLayoutMainMore = view.findViewById(R.id.linearLayout_main_fragment_more_details);
        linearLayoutMainMore.setOnClickListener(view1 -> {
            ((ViewPager2) requireActivity().findViewById(R.id.view_pager_main_activity_container)).setCurrentItem(1, true);
        });
        updateUiWithCurrentWeatherData(currentWeatherWrapper);
        updateUiWithFiveDaysData(fiveDaysWeatherWrapper);

        mainViewModel.getCurrentWeatherLiveData().observe(getActivity(), this::updateUiWithCurrentWeatherData);
        mainViewModel.getFiveDaysWeatherLiveData().observe(getActivity(), this::updateUiWithFiveDaysData);
    }

    private void updateUiWithCurrentWeatherData(CurrentWeatherWrapper currentWeatherWrapper) {
        if (currentWeatherWrapper == null) {
            Log.i(BuildConfig.LOG_TAG, "currentWeatherWrapper==null");
            mainViewModel.getNewData(getActivity());
        } else {
            Log.i(BuildConfig.LOG_TAG, "currentWeatherWrapper!=null");
            String dateTimeUpdated = getString(R.string.updated) + " " + DateFormat.getDateTimeInstance().format(new Date());
            ((TextView) view.findViewById(R.id.textView_main_updated)).setText(dateTimeUpdated);
            ((TextView) view.findViewById(R.id.textView_main_fragment_temperature))
                    .setText(String.valueOf((int) currentWeatherWrapper.getTemperature().getMetricTemperature().getValueOfMetricTemperature()));
            ((TextView) view.findViewById(R.id.textView_main_fragment_state)).setText(currentWeatherWrapper.getWeatherText());
            ((TextView) view.findViewById(R.id.textView_main_fragment_city)).setText(mainViewModel.getLocalizedName());
        }
    }

    private void updateUiWithFiveDaysData(FiveDaysWeatherWrapper fiveDaysWeatherWrapper) {
            recyclerAdapterMain.notifyDataSetChanged();
    }
}