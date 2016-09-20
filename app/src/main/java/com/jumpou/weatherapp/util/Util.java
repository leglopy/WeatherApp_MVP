package com.jumpou.weatherapp.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by zimburg on 19/09/16.
 */
public class Util {
    public static Drawable getDrawable(String name, Resources resources, String packageName) {
        final int resourceId = resources.getIdentifier(name, "drawable", packageName);
        return resources.getDrawable(resourceId);
    }
}
