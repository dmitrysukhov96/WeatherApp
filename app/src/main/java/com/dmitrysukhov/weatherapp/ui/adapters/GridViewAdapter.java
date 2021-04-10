package com.dmitrysukhov.weatherapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.model.wrappers.CurrentWeatherWrapper;

public class GridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final String[] gridViewDetailsNames;
    private final CurrentWeatherWrapper currentWeatherWrapper;

    public GridViewAdapter(Context c, CurrentWeatherWrapper currentWeatherWrapper) {
        mContext = c;
        this.currentWeatherWrapper = currentWeatherWrapper;
        gridViewDetailsNames = c.getResources().getStringArray(R.array.grid_view_names);
    }

    public int getCount() {
        return gridViewDetailsNames.length;
    }

    public Object getItem(int position) {
        return gridViewDetailsNames[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.cell_grid_details_fragment_text_values, viewGroup, false);
        } else {
            grid = view;
        }
        TextView textViewName = grid.findViewById(R.id.text_view_details_name);
        TextView textViewValue = grid.findViewById(R.id.text_view_details_value);
        textViewName.setText(gridViewDetailsNames[i]);
        if (currentWeatherWrapper!=null) {
            String[] valueStrings = {
                    String.valueOf(currentWeatherWrapper.getRealFeelTemperature().getMetricRealFeelTemperature().getValueRealFeel()),
                    String.valueOf(currentWeatherWrapper.getRelativeHumidity()),
                    currentWeatherWrapper.getPrecipOneHour().getMetricPrecip().getValueAndUnitPrecip(),
                    currentWeatherWrapper.getPressure().getMetricPressure().getValueAndUnitPressure(),
                    currentWeatherWrapper.getWind().getSpeedOfWind().getMetricSpeedOfWind().getValueAndUnitSpeedOfWind(),
                    String.valueOf(currentWeatherWrapper.getUVIndex())};
            textViewValue.setText(valueStrings[i]);
        }else textViewValue.setText(R.string.nothing);
        return grid;
    }
}