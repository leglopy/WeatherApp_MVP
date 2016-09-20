package com.jumpou.weatherapp;

/**
 * Created by zimburg on 15/09/16.
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

    boolean isActive();
}
