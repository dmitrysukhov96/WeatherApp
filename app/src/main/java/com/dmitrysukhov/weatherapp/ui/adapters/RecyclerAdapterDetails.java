package com.dmitrysukhov.weatherapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.model.wrappers.TwelveHoursWeatherWrapper;

import java.util.Calendar;

public class RecyclerAdapterDetails extends RecyclerView.Adapter<RecyclerAdapterDetails.SecondViewHolder> {

    private final TwelveHoursWeatherWrapper[] twelveHoursWeatherWrapper;

    public RecyclerAdapterDetails(TwelveHoursWeatherWrapper[] twelveHoursWeatherWrapper) {
        this.twelveHoursWeatherWrapper = twelveHoursWeatherWrapper;
    }

    @NonNull
    @Override
    public RecyclerAdapterDetails.SecondViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cardview_weather, viewGroup, false);
        return new RecyclerAdapterDetails.SecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterDetails.SecondViewHolder viewHolder, final int position) {
        int hour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY))+position;
        if (hour>=24) hour = hour-24;
        String hourOfItem = hour+":00";
        if (position == 0) {
            viewHolder.textViewDetailsTime.setText("Сейчас");
        } else {
            viewHolder.textViewDetailsTime.setText(hourOfItem);
        }
        if (twelveHoursWeatherWrapper!=null){
        String temperature = ((int)twelveHoursWeatherWrapper[position].getTemperature().getValue())+"°";
        viewHolder.textViewDetailsWeather.setText(temperature);
        viewHolder.textViewDetailsIconPhrases.setText(twelveHoursWeatherWrapper[position].getIconPhrase());

        int icon = twelveHoursWeatherWrapper[position].getWeatherIcon();
        if (icon>=0 & icon<=5 || icon==30){
            viewHolder.imageViewCardDetails.setImageResource(R.drawable.ic_sun);
        } else if (icon>=6 & icon<=11){
            viewHolder.imageViewCardDetails.setImageResource(R.drawable.ic_cloud);
        } else if (icon>=12 & icon<=18){
            viewHolder.imageViewCardDetails.setImageResource(R.drawable.rain);
        } else if (icon==20 || icon==21 || icon==32){
            viewHolder.imageViewCardDetails.setImageResource(R.drawable.ic_wind);
        } else if (icon>=22 & icon<=29 || icon==31){
            viewHolder.imageViewCardDetails.setImageResource(R.drawable.ic_snow);
        } else if (icon>=33 & icon<=44){
            viewHolder.imageViewCardDetails.setImageResource(R.drawable.ic_moon);
        }
    }}

    @Override
    public int getItemCount() {
        return 12;
    }

    public static class SecondViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDetailsTime;
        private final TextView textViewDetailsWeather;
        private final TextView textViewDetailsIconPhrases;
        private final ImageView imageViewCardDetails;

        public SecondViewHolder(@NonNull View view) {
            super(view);
            imageViewCardDetails = view.findViewById(R.id.image_view_card_details);
            textViewDetailsTime = view.findViewById(R.id.text_view_card_details_time);
            textViewDetailsWeather = view.findViewById(R.id.text_view_card_details_weather);
            textViewDetailsIconPhrases = view.findViewById(R.id.text_view_card_details_icon_phrase);
        }
    }
}
