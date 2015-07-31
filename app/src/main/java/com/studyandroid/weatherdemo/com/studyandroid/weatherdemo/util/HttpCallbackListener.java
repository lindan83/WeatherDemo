package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util;

public interface HttpCallbackListener {
    void onResponseSuccess(String response);

    void onResponseError(Exception e);
}
