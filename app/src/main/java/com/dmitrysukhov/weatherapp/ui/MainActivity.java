package com.dmitrysukhov.weatherapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.model.wrappers.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.FiveDaysWeatherWrapper;
import com.dmitrysukhov.weatherapp.model.wrappers.TwelveHoursWeatherWrapper;
import com.dmitrysukhov.weatherapp.ui.adapters.ScreenSlidePagerAdapter;
import com.dmitrysukhov.weatherapp.viewmodel.MainViewModel;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences mySharedPrefs;
    private ScreenSlidePagerAdapter fragmentStateAdapter;
    private String locationKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        createObservers();
        updateUISomehow();
    }

    private void initComponents() {
        mySharedPrefs = getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
        ViewPager2 viewPager = findViewById(R.id.view_pager_main_activity_container);
        fragmentStateAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(fragmentStateAdapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_main_container);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.sendThisLifecycle(getLifecycle());
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }

    private void createObservers() {
        mainViewModel.getCurrentWeatherLiveData().observe(MainActivity.this, currentWeatherWrapper -> {
            updateCurrentWeatherData(currentWeatherWrapper);
            updateUISomehow();
        });
        mainViewModel.getTwelveHoursWeatherLiveData().observe(MainActivity.this, twelveHoursWeatherWrapper -> {
            updateTwelveHoursData(twelveHoursWeatherWrapper);
            updateUISomehow();
        });
        mainViewModel.getFiveDaysWeatherLiveData().observe(MainActivity.this, fiveDaysWeatherWrapper -> {
            updateFiveDaysData(fiveDaysWeatherWrapper);
            updateUISomehow();
        });
    }


    private void updateUISomehow() {
        //TODO
        //RuntimeException: Fragment not attached to a context
        //fragmentStateAdapter.getWeatherMainFragment().setRecentData();
        //fragmentStateAdapter.getWeatherDetailsFragment().setRecentData();
    }

    private void updateCurrentWeatherData(CurrentWeatherWrapper currentWeatherWrapper) {
        mySharedPrefs.edit()
                .putString(getString(R.string.current_city), mainViewModel.getLocalizedName())
                .putString(getString(R.string.current_weather), currentWeatherWrapper.getWeatherText())
                .putString(getString(R.string.current_temperature),
                        String.valueOf((int) currentWeatherWrapper.getTemperature().getMetricTemperature().getValueOfMetricTemperature()))
                .putString("real_feel_temperature", String.valueOf(currentWeatherWrapper.getRealFeelTemperature().getMetricRealFeelTemperature().getValueRealFeel()))
                .putString("relative_humidity", String.valueOf(currentWeatherWrapper.getRelativeHumidity()))
                .putString("precip", currentWeatherWrapper.getPrecipOneHour().getMetricPrecip().getValueAndUnitPrecip())
                .putString("pressure", currentWeatherWrapper.getPressure().getMetricPressure().getValueAndUnitPressure())
                .putString("wind_speed", currentWeatherWrapper.getWind().getSpeedOfWind().getMetricSpeedOfWind().getValueAndUnitSpeedOfWind())
                .putString("uv_index", String.valueOf(currentWeatherWrapper.getUVIndex()))
                .putString("cloud_cover", String.valueOf(currentWeatherWrapper.getCloudCover()))
                .apply();
    }

    private void updateFiveDaysData(FiveDaysWeatherWrapper fiveDaysWeatherWrapper) {
        mySharedPrefs.edit()
                .putInt(getString(R.string.today_icon), fiveDaysWeatherWrapper.getDailyForecasts()[0].getDay().getIconNumber())
                .putInt(getString(R.string.tomorrow_icon), fiveDaysWeatherWrapper.getDailyForecasts()[1].getDay().getIconNumber())
                .putInt(getString(R.string.day_after_tomorrow_icon), fiveDaysWeatherWrapper.getDailyForecasts()[2].getDay().getIconNumber())
                .putString(getString(R.string.today_weather_phrase), fiveDaysWeatherWrapper.getDailyForecasts()[0].getDay().getIconPhrase())
                .putString(getString(R.string.tomorrow_weather_phrase), fiveDaysWeatherWrapper.getDailyForecasts()[0].getDay().getIconPhrase())
                .putString(getString(R.string.day_after_tomorrow_weather_phrase), fiveDaysWeatherWrapper.getDailyForecasts()[0].getDay().getIconPhrase())
                .putString(getString(R.string.today_temperature_minmax),
                        fiveDaysWeatherWrapper.getDailyForecasts()[0].getTemperature().getMinimum().getMinValue() + "° / " +
                                fiveDaysWeatherWrapper.getDailyForecasts()[0].getTemperature().getMaximum().getMaxValue() + "°")
                .putString(getString(R.string.tomorrow_temperature_minmax),
                        fiveDaysWeatherWrapper.getDailyForecasts()[1].getTemperature().getMinimum().getMinValue() + "° / " +
                                fiveDaysWeatherWrapper.getDailyForecasts()[1].getTemperature().getMaximum().getMaxValue() + "°")
                .putString(getString(R.string.day_after_tomorrow_temperature_minmax),
                        fiveDaysWeatherWrapper.getDailyForecasts()[2].getTemperature().getMinimum().getMinValue() + "° / " +
                                fiveDaysWeatherWrapper.getDailyForecasts()[2].getTemperature().getMaximum().getMaxValue() + "°")
                .apply();
    }

    private void updateTwelveHoursData(TwelveHoursWeatherWrapper[] twelveHoursWeatherWrapper) {
        for (int i = 0; i < twelveHoursWeatherWrapper.length; i++) {
            mySharedPrefs.edit()
                    .putString("twelve_weather_icons_" + i, String.valueOf(twelveHoursWeatherWrapper[i].getWeatherIcon()))
                    .putString("twelve_icon_phrases_" + i, twelveHoursWeatherWrapper[i].getIconPhrase())
                    .putString("twelve_temperatures_" + i, String.valueOf(twelveHoursWeatherWrapper[i].getTemperature().getValue()))
                    .apply();
        }
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
        String dateTimeUpdated = getString(R.string.updated)+" "+ DateFormat.getDateTimeInstance().format(new Date());
        //((TextView)findViewById(R.id.textView_main_updated)).setText(dateTimeUpdated);
        //приложение падает если фрагмент с этим текствью за пределами экрана.
        if (checkGps()) mainViewModel.getNewData(this);
        swipeRefreshLayout.setRefreshing(false);
    }


    //GPS methods
    private boolean checkGps() {
        boolean methodState = false;
        if (checkGpsPermission()) {
            methodState = checkGpsEnabled();
        }
        return methodState;
    }

    private boolean checkGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.please_turn_on_gps)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, (dialog, which) -> startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 65)
                    ).setNegativeButton(R.string.no_thanks, null)
                    .show();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 65) {
            checkGpsEnabled();
        }
    }

    private boolean checkGpsPermission() {
        boolean state = false;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            state = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        return state;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.gps_rationale)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, (dialog, which) -> ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44)
                    ).setNegativeButton(R.string.cancel, null)
                    .show();
        }
    }
}