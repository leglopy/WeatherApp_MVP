package com.jumpou.weatherapp.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jumpou.weatherapp.R;
import com.jumpou.weatherapp.util.ActivityUtils;
import com.jumpou.weatherapp.util.Constants;
import com.jumpou.weatherapp.util.Injection;


public class WeatherActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (weatherFragment == null) {
            weatherFragment = WeatherFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), weatherFragment, R.id.contentFrame);
        }

        new WeatherPresenter(Injection.provideNetworkService(getApplicationContext()).getWeatherAPI(), Injection.provideDataService(getApplicationContext()), weatherFragment);
    }
}
