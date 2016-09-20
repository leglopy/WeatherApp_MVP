package com.jumpou.weatherapp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jumpou.weatherapp.util.Constants;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zimburg on 19/09/16.
 */
public class DataServiceImpl implements Constants, DataService {
    private static DataServiceImpl INSTANCE = null;
    private TinyDB mTinyDB = null;

    private DataServiceImpl(@NonNull Context context) {
        checkNotNull(context);
        mTinyDB = new TinyDB(context);
    }

    public static DataServiceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataServiceImpl(context);
        }
        return INSTANCE;
    }

    public void saveWeatherCities(ArrayList<String> weatherCities) {
        mTinyDB.putListString(KEY_WEATHER_ARRAY_LIST, weatherCities);
    }

    public ArrayList<String> getWeatherCities() {
        return mTinyDB.getListString(KEY_WEATHER_ARRAY_LIST);
    }
}
