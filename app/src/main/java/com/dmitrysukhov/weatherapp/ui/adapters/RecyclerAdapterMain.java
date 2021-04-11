package com.dmitrysukhov.weatherapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.model.wrappers.FiveDaysWeatherWrapper;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.FirstViewHolder> {

    private final String[] stringArrayMainDays;
    private FiveDaysWeatherWrapper fiveDaysWeatherWrapper;

    public RecyclerAdapterMain(Context context, FiveDaysWeatherWrapper fiveDaysWeatherWrapper) {
        this.fiveDaysWeatherWrapper = fiveDaysWeatherWrapper;
        stringArrayMainDays = context.getResources().getStringArray(R.array.day_main);
    }

    @NonNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cardview_recent, viewGroup, false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstViewHolder viewHolder, final int position) {
        viewHolder.textViewDay.setText(stringArrayMainDays[position]);
        if (fiveDaysWeatherWrapper != null) {
            viewHolder.textViewState.setText(fiveDaysWeatherWrapper.getDailyForecasts().get(position).getDay().getIconPhrase());
            String cardTemperature = fiveDaysWeatherWrapper.getDailyForecasts().get(position).getTemperature().getMinimum().getMinValue()
                    + "° / " + fiveDaysWeatherWrapper.getDailyForecasts().get(position).getTemperature().getMaximum().getMaxValue() + "°";
            viewHolder.textViewTemperature.setText(cardTemperature);
            int imagesMainWeather = fiveDaysWeatherWrapper.getDailyForecasts().get(position).getDay().getIconNumber();
            if (imagesMainWeather >= 0 & imagesMainWeather <= 5 || imagesMainWeather == 30) {
                viewHolder.iconWeather.setImageResource(R.drawable.ic_sun);
            } else if (imagesMainWeather >= 6 & imagesMainWeather <= 11) {
                viewHolder.iconWeather.setImageResource(R.drawable.ic_cloud);
            } else if (imagesMainWeather >= 12 & imagesMainWeather <= 18) {
                viewHolder.iconWeather.setImageResource(R.drawable.rain);
            } else if (imagesMainWeather == 20 || imagesMainWeather == 21 || imagesMainWeather == 32) {
                viewHolder.iconWeather.setImageResource(R.drawable.ic_wind);
            } else if (imagesMainWeather >= 22 & imagesMainWeather <= 29 || imagesMainWeather == 31) {
                viewHolder.iconWeather.setImageResource(R.drawable.ic_snow);
            } else if (imagesMainWeather >= 33 & imagesMainWeather <= 44) {
                viewHolder.iconWeather.setImageResource(R.drawable.ic_moon);
            }
        } else {
            viewHolder.iconWeather.setImageResource(R.drawable.ic_sun);
            viewHolder.textViewState.setText(R.string.nothing);
            viewHolder.textViewTemperature.setText(R.string.nothing);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setForecast(FiveDaysWeatherWrapper forecast) {
        this.fiveDaysWeatherWrapper = forecast;
    }

    public static class FirstViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDay;
        private final TextView textViewState;
        private final TextView textViewTemperature;
        private final ImageView iconWeather;

        public FirstViewHolder(@NonNull View view) {
            super(view);
            iconWeather = view.findViewById(R.id.image_view_card_view_icon_weather);
            textViewDay = view.findViewById(R.id.text_view_card_day);
            textViewState = view.findViewById(R.id.text_view_card_state);
            textViewTemperature = view.findViewById(R.id.text_view_card_temperature);
        }
    }
}
