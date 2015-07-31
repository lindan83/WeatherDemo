package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util;

import android.text.TextUtils;

import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.City;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Province;

import java.util.ArrayList;
import java.util.List;

public class WeatherUtil {
    private WeatherUtil() {}

    public static List<Province> parseProvinces(String provinceString) {
        if(TextUtils.isEmpty(provinceString)) {
            return null;
        }
        String[] provinceArray = provinceString.split(",");
        if(provinceArray != null) {
            List<Province> provinces = new ArrayList<>();
            for(String provinceElement : provinceArray) {
                Province province = new Province();
                String[] values = provinceElement.split("\\|");
                province.setProvinceId(values[0]);
                province.setProvinceName(values[1]);
                provinces.add(province);
            }
            return provinces;
        }
        return null;
    }

    public static List<City> parseCities(String cityString) {
        if(TextUtils.isEmpty(cityString)) {
            return null;
        }
        String[] cityArray = cityString.split(",");
        if(cityArray != null) {
            List<City> cities = new ArrayList<>();
            for(String cityElement : cityArray) {
                City city = new City();
                String[] values = cityElement.split("\\|");
                city.setCityId(values[0]);
                city.setCityName(values[1]);
                cities.add(city);
            }
            return cities;
        }
        return null;
    }
}
