package com.jumpou.weatherapp.weather;

import com.jumpou.weatherapp.BasePresenter;
import com.jumpou.weatherapp.BaseView;
import com.jumpou.weatherapp.data.WeatherItem;

import java.util.ArrayList;

/**
 * Created by zimburg on 15/09/16.
 */
public class WeatherContract {
    interface View extends BaseView<Presenter> {
        void showAddWeatherItem();

        void showWeatherItems(ArrayList<WeatherItem> weatherItemsList);

        void addCityDialogDismiss();

        void displayLoadingIndicator(Boolean isVisible);

        void displayNoNetworkMessage();
    }

    interface Presenter extends BasePresenter {
        void loadWeatherItems();

        void addWeatherItem();

        void trashWeatherTouched(int position);

        void addCityDialogDismiss();

        void retryInternetAsked();

    }
}
