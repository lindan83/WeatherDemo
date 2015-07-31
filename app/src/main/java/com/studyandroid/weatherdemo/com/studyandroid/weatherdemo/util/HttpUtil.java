package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {
    private HttpUtil() {
    }

    public static void sendHttpPostRequest(final String urlString, final Map<String, String[]> parameters, final HttpCallbackListener listener) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                StringBuilder response = new StringBuilder();
                StringBuilder request = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    if (parameters != null && parameters.size() > 0) {
                        connection.setDoOutput(true);
                        request = new StringBuilder();
                        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                            String paramName = entry.getKey();
                            String[] paramValues = entry.getValue();
                            request.append(paramName).append("=");
                            for (int i = 0; i < paramValues.length; i++) {
                                if (i < paramValues.length - 1) {
                                    request.append(paramValues[i]).append("&").append(paramName).append("=");
                                } else {
                                    request.append(paramValues[i]);
                                }
                            }
                            request.append("&");
                        }
                        request.deleteCharAt(request.length() - 1);
                        Log.d("request string", request.toString());
                    } else {
                        connection.setDoOutput(false);
                    }
                    connection.setDoInput(true);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    if (request != null && request.length() > 0) {
                        connection.getOutputStream().write(request.toString().getBytes("UTF-8"));
                    }
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onResponseSuccess(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onResponseError(e);
                    }
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }

    public static void sendHttpGetRequest(final String urlString, final HttpCallbackListener listener) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                StringBuilder response = new StringBuilder();
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(false);
                    connection.setDoInput(true);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onResponseSuccess(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onResponseError(e);
                    }
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }
}
