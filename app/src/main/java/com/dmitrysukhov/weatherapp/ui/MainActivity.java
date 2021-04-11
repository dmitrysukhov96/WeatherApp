package com.dmitrysukhov.weatherapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.ui.adapters.ScreenSlidePagerAdapter;
import com.dmitrysukhov.weatherapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ViewPager2 viewPager = findViewById(R.id.view_pager_main_activity_container);
        ScreenSlidePagerAdapter fragmentStateAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(fragmentStateAdapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_main_container);
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
        refreshData();
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
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
        } else {
            if (checkGpsEnabled()) mainViewModel.getNewData(this);
        }
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
}