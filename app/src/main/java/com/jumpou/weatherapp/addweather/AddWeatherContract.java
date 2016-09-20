package com.jumpou.weatherapp.addweather;

import com.jumpou.weatherapp.BasePresenter;
import com.jumpou.weatherapp.BaseView;

/**
 * Created by zimburg on 19/09/16.
 */
public class AddWeatherContract {
    interface View extends BaseView<Presenter> {
        void closeView();

        void displayErrorMessage(int stringRessource);

        void displayLoadingIndicator(Boolean isVisible);
    }

    interface Presenter extends BasePresenter {
        void addCity(String city);
    }
}
