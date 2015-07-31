package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util;

import android.text.TextUtils;

import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.database.WeatherDBOperation;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.City;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.County;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Province;

import java.util.ArrayList;
import java.util.List;

public class WeatherUtil {
    private static WeatherDBOperation dbOperation = WeatherDBOperation.getInstance();

    private WeatherUtil() {
    }

    public static List<Province> parseProvinces(String provinceString) {
        if (TextUtils.isEmpty(provinceString)) {
            return null;
        }
        String[] provinceArray = provinceString.split(",");
        if (provinceArray != null) {
            List<Province> provinces = new ArrayList<>();
            for (String provinceElement : provinceArray) {
                Province province = new Province();
                String[] values = provinceElement.split("\\|");
                province.setProvinceCode(values[0]);
                province.setProvinceName(values[1]);
                provinces.add(province);
                //缓存到本地数据库中
                dbOperation.saveProvince(province);
            }
            return provinces;
        }
        return null;
    }

    public static List<City> parseCities(String cityString, int provinceId) {
        if (TextUtils.isEmpty(cityString)) {
            return null;
        }
        String[] cityArray = cityString.split(",");
        if (cityArray != null) {
            List<City> cities = new ArrayList<>();
            for (String cityElement : cityArray) {
                City city = new City();
                String[] values = cityElement.split("\\|");
                city.setCityCode(values[0]);
                city.setCityName(values[1]);
                city.setProvinceId(provinceId);
                cities.add(city);
                //缓存到本地数据库中
                dbOperation.saveCity(city);
            }
            return cities;
        }
        return null;
    }

    public static List<County> parseCounties(String countyString, int cityId) {
        if (TextUtils.isEmpty(countyString)) {
            return null;
        }
        String[] countyArray = countyString.split(",");
        if (countyArray != null) {
            List<County> counties = new ArrayList<>();
            for (String cityElement : countyArray) {
                County county = new County();
                String[] values = cityElement.split("\\|");
                county.setCountyCode(values[0]);
                county.setCountyName(values[1]);
                county.setCityId(cityId);
                counties.add(county);
                //缓存到本地数据库中
                dbOperation.saveCounty(county);
            }
            return counties;
        }
        return null;
    }
}
