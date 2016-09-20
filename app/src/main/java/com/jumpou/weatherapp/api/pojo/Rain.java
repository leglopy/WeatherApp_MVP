package com.jumpou.weatherapp.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zimburg on 19/09/16.
 */
public class Rain {
    @SerializedName("3h")
    @Expose
    public Float rainLevelLastThreeHours;
}
