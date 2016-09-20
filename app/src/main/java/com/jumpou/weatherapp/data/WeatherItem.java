package com.jumpou.weatherapp.data;

import android.support.annotation.NonNull;

import com.jumpou.weatherapp.util.WeatherUtil;

/**
 * Created by zimburg on 18/09/16.
 */
public class WeatherItem {
    private final String mCity;
    private final String mImg;
    private final float mDegreeCelsius;
    private final int mHumidity;
    private final float mRainLevel;
    private final float mWind;

    public WeatherItem(@NonNull String city, @NonNull String img, @NonNull float degreeCelsius, @NonNull int humidity, @NonNull float rainLevel, @NonNull float wind) {
        mCity = city;
        mImg = img;
        mDegreeCelsius = degreeCelsius;
        mHumidity = humidity;
        mRainLevel = rainLevel;
        mWind = wind;
    }

    public WeatherItem(@NonNull String city, @NonNull String img, @NonNull double kelvin, @NonNull int humidity, @NonNull float rainLevel, @NonNull float wind) {
        mCity = city;
        mImg = img;
        mDegreeCelsius = WeatherUtil.convertKelvinInCelsius(kelvin);
        mHumidity = humidity;
        mRainLevel = rainLevel;
        mWind = wind;
    }

    public String getCity() {
        return mCity;
    }

    public String getImg() {
        return mImg;
    }

    public float getDegreeCelsius() {
        return mDegreeCelsius;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public float getRainLevel() {
        return mRainLevel;
    }

    public float getWind() {
        return mWind;
    }
}
