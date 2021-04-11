package com.dmitrysukhov.weatherapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.model.wrappers.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.TwelveHoursWeatherWrapper;
import com.dmitrysukhov.weatherapp.ui.adapters.GridViewAdapter;
import com.dmitrysukhov.weatherapp.ui.adapters.RecyclerAdapterDetails;
import com.dmitrysukhov.weatherapp.viewmodel.MainViewModel;

public class WeatherDetailsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private RecyclerAdapterDetails recyclerAdapterDetails;
    private GridViewAdapter gridViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CurrentWeatherWrapper currentWeatherWrapper = mainViewModel.getCurrentWeatherLiveData().getValue();
        TwelveHoursWeatherWrapper[] twelveHoursWeatherWrapper = mainViewModel.getTwelveHoursWeatherLiveData().getValue();
        RecyclerView recyclerViewDetails = view.findViewById(R.id.recycler_view_details_weather);
        recyclerAdapterDetails = new RecyclerAdapterDetails(twelveHoursWeatherWrapper);
        recyclerViewDetails.setAdapter(recyclerAdapterDetails);
        recyclerViewDetails.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        GridView gridview = view.findViewById(R.id.grid_view_details);
        gridViewAdapter = new GridViewAdapter(requireContext(), currentWeatherWrapper);
        gridview.setAdapter(gridViewAdapter);

        mainViewModel.getCurrentWeatherLiveData().observe(getActivity(), this::updateUiWithCurrentWeatherData);
        mainViewModel.getTwelveHoursWeatherLiveData().observe(getActivity(), this::updateUiWithTwelveHoursData);

        if (currentWeatherWrapper != null)
            ((TextView) view.findViewById(R.id.textview_details_cloud_cover))
                    .setText(String.valueOf(currentWeatherWrapper.getCloudCover()));
    }

    private void updateUiWithCurrentWeatherData(CurrentWeatherWrapper currentWeatherWrapper) {
        gridViewAdapter.setCurrentWeather(currentWeatherWrapper);
        gridViewAdapter.notifyDataSetChanged();
    }

    private void updateUiWithTwelveHoursData(TwelveHoursWeatherWrapper[] twelveHoursWeatherWrappers) {
        recyclerAdapterDetails.setTwelweHoursWeather(twelveHoursWeatherWrappers);
        recyclerAdapterDetails.notifyDataSetChanged();
    }
}