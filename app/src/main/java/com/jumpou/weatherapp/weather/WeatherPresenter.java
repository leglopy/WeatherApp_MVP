package com.jumpou.weatherapp.weather;


import android.support.annotation.NonNull;

import com.jumpou.weatherapp.api.WeatherAPI;
import com.jumpou.weatherapp.api.pojo.WeatherData;
import com.jumpou.weatherapp.data.DataServiceImpl;
import com.jumpou.weatherapp.data.WeatherItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zimburg on 15/09/16.
 */
public class WeatherPresenter implements WeatherContract.Presenter {
    private final WeatherContract.View mView;
    private final WeatherAPI mWeatherAPI;
    private final ArrayList<WeatherItem> mWeatherItems = new ArrayList<>();
    private final DataServiceImpl mDataService;
    private boolean isDoingRequest = false;
    private boolean isShownError = false;

    public WeatherPresenter(@NonNull WeatherAPI weatherAPI, @NonNull DataServiceImpl dataService, @NonNull WeatherContract.View view) {
        mView = checkNotNull(view, "MainView cannot be null!");
        mView.setPresenter(this);
        mWeatherAPI = checkNotNull(weatherAPI, "WeatherAPI cannot be null!");
        mDataService = checkNotNull(dataService, "DataService cannot be null!");
    }

    @Override
    public void start() {
        loadWeatherItems();
    }

    @Override
    public void loadWeatherItems() {
        if (mView.isActive() && !isDoingRequest) {
            ArrayList<String> weatherCities = mDataService.getWeatherCities();
            if (weatherCities.size() == 0) {
                mView.showWeatherItems(mWeatherItems);
                return;
            }
            isDoingRequest = true;
            isShownError = false;
            mView.displayLoadingIndicator(true);
            mWeatherItems.clear();

            for (String weatherCity : weatherCities) {
                Call<WeatherData> call = mWeatherAPI.getWeatherCity(weatherCity);
                call.enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, retrofit2.Response<WeatherData> response) {
                        if (response.isSuccessful()) {
                            WeatherData currentWeatherData = response.body();
                            String city = "";
                            String img = "";
                            double kelvin = 0;
                            int humidty = 0;
                            float windSpeed = 0;
                            float rainLevel = 0;
                            if (currentWeatherData != null) {
                                city = currentWeatherData.name;
                                if (currentWeatherData.weather != null) {
                                    if (currentWeatherData.weather.size() > 0)
                                        img = currentWeatherData.weather.get(0).icon;
                                }

                                if (currentWeatherData.main != null) {
                                    kelvin = (currentWeatherData.main.temp == null) ? 0 : currentWeatherData.main.temp;
                                    humidty = (currentWeatherData.main.humidity == null) ? 0 : currentWeatherData.main.humidity;
                                }
                                if (currentWeatherData.wind != null) {
                                    windSpeed = (currentWeatherData.wind.speed == null) ? 0 : currentWeatherData.wind.speed;
                                }
                                if (currentWeatherData.rain != null) {
                                    rainLevel = (currentWeatherData.rain.rainLevelLastThreeHours == null) ? 0 : currentWeatherData.rain.rainLevelLastThreeHours;
                                }
                            }
                            WeatherItem weatherItem = new WeatherItem(city, img, kelvin, humidty, rainLevel, windSpeed);
                            mWeatherItems.add(weatherItem);
                            mView.showWeatherItems(mWeatherItems);
                        } else {
                            if (!isShownError) {
                                mView.displayNoNetworkMessage();
                                isShownError = true;
                            }

                        }
                        isDoingRequest = false;
                        mView.displayLoadingIndicator(false);
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        isDoingRequest = false;
                        mView.displayLoadingIndicator(false);
                        mView.displayNoNetworkMessage();
                    }
                });
            }
        }

    }

    @Override
    public void addWeatherItem() {
        mView.showAddWeatherItem();
    }

    @Override
    public void trashWeatherTouched(int position) {
        mWeatherItems.remove(position);
        ArrayList<String> weatherCities = mDataService.getWeatherCities();
        weatherCities.remove(position);
        mDataService.saveWeatherCities(weatherCities);
        mView.showWeatherItems(mWeatherItems);
    }

    @Override
    public void addCityDialogDismiss() {
        loadWeatherItems();
    }

    @Override
    public void retryInternetAsked() {
        loadWeatherItems();
    }
}
