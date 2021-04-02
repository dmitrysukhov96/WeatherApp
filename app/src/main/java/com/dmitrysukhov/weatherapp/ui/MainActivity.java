package com.dmitrysukhov.weatherapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dmitrysukhov.weatherapp.BuildConfig;
import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.model.CurrentWeatherWrapper;
import com.dmitrysukhov.weatherapp.ui.adapters.ScreenSlidePagerAdapter;
import com.dmitrysukhov.weatherapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = findViewById(R.id.view_pager_main_activity_container);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_main_container);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (savedInstanceState==null) getRecentData();
        swipeRefreshLayout.setOnRefreshListener(this::getRecentData);
    }

    private void getRecentData() {
        if (checkGps()) {
            swipeRefreshLayout.setRefreshing(true);
            mainViewModel.getLocationKey(this).observe(this, newLocationKey -> {
                mainViewModel.getCurrentWeatherLiveData(newLocationKey).observe
                        (MainActivity.this, currentWeatherWrapper -> {
                            swipeRefreshLayout.setRefreshing(false);
                            ((TextView) findViewById(R.id.textView_main_fragment_index_of_air_quality))
                                    .setText(mainViewModel.getLocalizedName());
                            fillUIWithCurrentWeather(currentWeatherWrapper);
                        });
                mainViewModel.getTwelveHoursWeatherLiveData(newLocationKey).observe
                        (MainActivity.this, twelveHoursWeatherWrapper -> {
                            //do smth with twelveHoursWeatherWrapper. set text etc
                        });
                mainViewModel.getFiveDaysWeatherLiveData(newLocationKey).observe
                        (MainActivity.this, fiveDaysWeatherWrapper -> {
                            //do smth with fivedaysweatherwrapper. set text etc
                        });
            });
        }
    }

    private void fillUIWithCurrentWeather(CurrentWeatherWrapper currentWeatherWrapper) {
        ((TextView) findViewById(R.id.textView_main_fragment_state)).setText(currentWeatherWrapper.getWeatherText());
        double tempMetricVal = currentWeatherWrapper.getTemperature().getMetric().getValue();
        ((TextView) findViewById(R.id.textView_main_fragment_temperature)).setText((String.valueOf((int) tempMetricVal)));
    }


    //GPS methods
    private boolean checkGps() {
        boolean methodState = false;
        if (checkGpsPermission()) {
            methodState = checkGpsEnabled();
        }
        Log.i(BuildConfig.LOG_TAG, "checkGPS returns " + methodState);
        return methodState;
    }

    private boolean checkGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i(BuildConfig.LOG_TAG, "checkGpsEnabled returns true");
            return true;
        } else {
            Log.i(BuildConfig.LOG_TAG, "checkGpsEnabled returns false");
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
        Log.i(BuildConfig.LOG_TAG, "checkGpsPermission returns " + state);
        return state;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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