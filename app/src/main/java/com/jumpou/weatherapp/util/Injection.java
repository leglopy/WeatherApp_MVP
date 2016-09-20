package com.jumpou.weatherapp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jumpou.weatherapp.api.NetworkService;
import com.jumpou.weatherapp.data.DataServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zimburg on 15/09/16.
 */
public class Injection {
    public static NetworkService provideNetworkService(@NonNull Context context) {
        checkNotNull(context);
        return NetworkService.getInstance(context);
    }

    public static DataServiceImpl provideDataService(@NonNull Context context) {
        checkNotNull(context);
        return DataServiceImpl.getInstance(context);
    }
}
