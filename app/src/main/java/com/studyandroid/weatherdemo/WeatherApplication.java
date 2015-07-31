package com.studyandroid.weatherdemo;

import android.app.Application;
import android.content.Context;

public class WeatherApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
