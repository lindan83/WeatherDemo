package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.studyandroid.weatherdemo.WeatherApplication;

public class BaseActivity extends Activity {
    protected WeatherApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (WeatherApplication) getApplication();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
