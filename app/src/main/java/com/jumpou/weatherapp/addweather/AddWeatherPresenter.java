package com.jumpou.weatherapp.addweather;

import android.support.annotation.NonNull;

import com.jumpou.weatherapp.R;
import com.jumpou.weatherapp.api.WeatherAPI;
import com.jumpou.weatherapp.api.pojo.WeatherData;
import com.jumpou.weatherapp.data.DataServiceImpl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zimburg on 19/09/16.
 */
public class AddWeatherPresenter implements AddWeatherContract.Presenter {
    private final AddWeatherContract.View mView;
    private final WeatherAPI mWeatherAPI;
    private final DataServiceImpl mDataService;
    private Boolean isDoingRequest = false;

    public AddWeatherPresenter(@NonNull AddWeatherContract.View view, @NonNull WeatherAPI weatherAPI, @NonNull DataServiceImpl dataService) {
        mView = checkNotNull(view, "AddWeatherView cannot be null!");
        mWeatherAPI = checkNotNull(weatherAPI, "WeatherAPI cannot be null!");
        mDataService = checkNotNull(dataService, "DataService cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void addCity(final String city) {
        if (mView.isActive() && !isDoingRequest) {
            isDoingRequest = true;
            mView.displayLoadingIndicator(true);
            Call<WeatherData> call = mWeatherAPI.getWeatherCity(city);
            call.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, retrofit2.Response<WeatherData> response) {
                    if (response.isSuccessful()) {
                        if (response.body().cod == 200) {
                            ArrayList<String> weatherCities;
                            if (mDataService.getWeatherCities() != null)
                                weatherCities = mDataService.getWeatherCities();
                            else
                                weatherCities = new ArrayList<String>();
                            weatherCities.add(city);
                            mDataService.saveWeatherCities(weatherCities);
                            mView.closeView();
                        } else {
                            mView.displayErrorMessage(R.string.country_not_found);
                        }
                    } else {
                        mView.displayErrorMessage(R.string.error_msg_internet);
                    }
                    isDoingRequest = false;
                    mView.displayLoadingIndicator(false);
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    mView.displayErrorMessage(R.string.error_msg_internet);
                    isDoingRequest = false;
                    mView.displayLoadingIndicator(false);
                }
            });
        }
    }

    @Override
    public void start() {

    }
}
