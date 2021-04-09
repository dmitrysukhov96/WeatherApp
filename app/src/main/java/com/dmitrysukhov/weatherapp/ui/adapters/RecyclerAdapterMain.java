package com.dmitrysukhov.weatherapp.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.BuildConfig;
import com.dmitrysukhov.weatherapp.R;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.FirstViewHolder> {

    Context context;
    private final String[] stringArrayAdapterDays;
    private final String[] stringArrayAdapterWeatherText;
    private final String[] stringArrayAdapterTemperature;
    int[] imagesMainWeather;

    public RecyclerAdapterMain(Context ct, String[] s1, String[] s2, String[] s3, int[] img) {
        context = ct;
        stringArrayAdapterDays = s1;
        stringArrayAdapterWeatherText = s2;
        stringArrayAdapterTemperature = s3;
        imagesMainWeather = img;
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
        viewHolder.textViewDay.setText(stringArrayAdapterDays[position]);
        viewHolder.textViewState.setText(String.format(stringArrayAdapterWeatherText[position]));
        viewHolder.textViewTemperature.setText(stringArrayAdapterTemperature[position]);
        if (imagesMainWeather[position]>=0 & imagesMainWeather[position]<=5 || imagesMainWeather[position]==30){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_sun);
        } else if (imagesMainWeather[position]>=6 & imagesMainWeather[position]<=11){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_cloud);
        } else if (imagesMainWeather[position]>=12 & imagesMainWeather[position]<=18){
            viewHolder.iconWeather.setImageResource(R.drawable.rain);
        } else if (imagesMainWeather[position]==20 || imagesMainWeather[position]==21 || imagesMainWeather[position]==32){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_wind);
        } else if (imagesMainWeather[position]>=22 & imagesMainWeather[position]<=29 || imagesMainWeather[position]==31){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_snow);
        } else if (imagesMainWeather[position]>=33 & imagesMainWeather[position]<=44){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_moon);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
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
