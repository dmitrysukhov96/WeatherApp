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

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.FirstViewHolder> {

    Context context;
    private final String[] stringArrayAdapterDays;
    private final String[] stringArrayAdapterWeatherText;
    private final String[] stringArrayAdapterTemperature;
    int[] images;

    public RecyclerAdapterMain(Context ct, String[] s1, String[] s2, String[] s3, int[] img) {
        context = ct;
        stringArrayAdapterDays = s1;
        stringArrayAdapterWeatherText = s2;
        stringArrayAdapterTemperature = s3;
        images = img;
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
        viewHolder.textViewState.setText(String.format("%s%s", viewHolder.textViewState.getText().toString(), stringArrayAdapterWeatherText[position]));
        viewHolder.textViewTemperature.setText(stringArrayAdapterTemperature[position]);
        if (images[position]>=0 & images[position]<=5 || images[position]==30){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_sun);
        } else if (images[position]>=6 & images[position]<=11){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_cloud);
        } else if (images[position]>=12 & images[position]<=18){
            viewHolder.iconWeather.setImageResource(R.drawable.rain);
        } else if (images[position]==20 || images[position]==21 || images[position]==32){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_wind);
        } else if (images[position]>=22 & images[position]<=29 || images[position]==31){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_snow);
        } else if (images[position]>=33 & images[position]<=44){
            viewHolder.iconWeather.setImageResource(R.drawable.ic_moon);
        }
    }

    @Override
    public int getItemCount() {
        return images.length;
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
