package com.dmitrysukhov.weatherapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.ui.adapters.GridViewAdapter;
import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.ui.adapters.RecyclerAdapterDetails;

public class WeatherDetailsFragment extends Fragment {

    private SharedPreferences mySharedPrefs;
    private RecyclerAdapterDetails recyclerAdapterDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySharedPrefs = getActivity().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
        ((TextView) view.findViewById(R.id.textview_details_cloud_cover)).setText(String.format(" %s", mySharedPrefs.getString("cloud_cover", getString(R.string.nothing))));
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_details_weather);
        String[] gridViewDetailsValues = {
                mySharedPrefs.getString("real_feel_temperature", getString(R.string.nothing)),
                mySharedPrefs.getString("relative_humidity", getString(R.string.nothing)),
                mySharedPrefs.getString("precip", getString(R.string.nothing)),
                mySharedPrefs.getString("pressure", getString(R.string.nothing)),
                mySharedPrefs.getString("wind_speed", getString(R.string.nothing)),
                mySharedPrefs.getString("uv_index", getString(R.string.nothing)),
        };
        recyclerAdapterDetails = new RecyclerAdapterDetails(requireContext());
        recyclerView.setAdapter(recyclerAdapterDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        GridView gridview = view.findViewById(R.id.grid_view_details);
        gridview.setAdapter(new GridViewAdapter(requireContext(), gridViewDetailsValues));
    }
}