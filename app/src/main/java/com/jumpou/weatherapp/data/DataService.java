package com.jumpou.weatherapp.data;

import java.util.ArrayList;

/**
 * Created by zimburg on 19/09/16.
 */
public interface DataService {
    void saveWeatherCities(ArrayList<String> weatherCities);

    ArrayList<String> getWeatherCities();
}
