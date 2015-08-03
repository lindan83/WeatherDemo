package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity;

import java.io.Serializable;

/**
 * {"weatherinfo":
 * {"city":"昆山","cityid":"101190404","temp1":"21℃","temp2":"9℃",
 * "weather":"多云转小雨","img1":"d1.gif","img2":"n7.gif","ptime":"11:00"}}
 */
public class Weather implements Serializable {
    private String weatherCode;
    private String city;
    private String cityId;
    private String temp1;
    private String temp2;
    private String weather;
    private String img1;
    private String img2;
    private String publishTime;
    private String loadTime;

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }
}