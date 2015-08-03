package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.studyandroid.weatherdemo.WeatherApplication;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.config.UrlConfig;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.database.WeatherDBOperation;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.City;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.County;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Province;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Weather;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherUtil {
    private static WeatherDBOperation dbOperation = WeatherDBOperation.getInstance();

    private WeatherUtil() {
    }

    /**
     * 解析省份列表数据并缓存
     *
     * @param provinceString
     * @return
     */
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

    /**
     * 解析城市列表数据并缓存
     *
     * @param cityString
     * @param provinceId
     * @return
     */
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

    /**
     * 解析县镇数据并缓存
     *
     * @param countyString
     * @param cityId
     * @return
     */
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

    /**
     * 根据天气码获取天气信息并解析保存
     * <p/>
     * {"weatherinfo":
     * {"city":"昆山","cityid":"101190404","temp1":"21℃","temp2":"9℃",
     * "weather":"多云转小雨","img1":"d1.gif","img2":"n7.gif","ptime":"11:00"}}
     *
     * @param weatherCode
     * @return 天气JSON数据
     */
    public static void getWeatherInfo(final String weatherCode) {
        if (TextUtils.isEmpty(weatherCode)) {
            return;
        }

        HttpUtil.sendHttpGetRequest(UrlConfig.getWeatherURL(weatherCode), new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                parseWeatherInfo(weatherCode, response);
            }

            @Override
            public void onResponseError(Exception e) {
                e.printStackTrace();
                Toast.makeText(WeatherApplication.getContext(), "获取天气数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 解析天气数据并保存
     *
     * @param weatherCode   天气码
     * @param weatherString 天气JSON数据
     */
    public static void parseWeatherInfo(String weatherCode, String weatherString) {
        if (TextUtils.isEmpty(weatherString)) {
            return;
        }
        try {
            Weather weather = new Weather();
            JSONObject jsonObject = new JSONObject((weatherString));
            JSONObject weatherObj = jsonObject.getJSONObject("weatherinfo");
            weather.setCity(weatherObj.getString("city"));
            weather.setCityId(weatherObj.getString("cityid"));
            weather.setTemp1(weatherObj.getString("temp1"));
            weather.setTemp2(weatherObj.getString("temp2"));
            weather.setWeather(weatherObj.getString("weather"));
            weather.setImg1(weatherObj.getString("img1"));
            weather.setImg2(weatherObj.getString("img2"));
            weather.setPublishTime(weatherObj.getString("ptime"));
            saveWeatherInfo(weatherCode, weather);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存天气信息
     *
     * @param weatherCode
     * @param weather
     */
    public static void saveWeatherInfo(String weatherCode, Weather weather) {
        SharedPreferences.Editor editor = WeatherApplication.getContext().getSharedPreferences("weather_prefs", Context.MODE_PRIVATE).edit();
        editor.putBoolean("city_selected", true).putString("city_name", weather.getCity()).putString("weather_code", weatherCode).putString("temp1", weather.getTemp1()).putString("temp2", weather.getTemp2()).putString("weather_desc", weather.getWeather()).putString("publish_time", weather.getPublishTime()).putString("current_time", DateTimeUtil.getDateTime(DateTimeUtil.FORMAT_LONG_DATE_TIME)).commit();
    }

    /**
     * 从SharedPreferences加载天气数据
     *
     * @return
     */
    public static Weather loadWeather() {
        SharedPreferences pref = WeatherApplication.getContext().getSharedPreferences("weather_prefs", Context.MODE_PRIVATE);
        Weather weather = new Weather();
        weather.setCity(pref.getString("city_name", ""));
        weather.setWeatherCode(pref.getString("weather_code", ""));
        weather.setTemp1(pref.getString("temp1", ""));
        weather.setTemp2(pref.getString("temp2", ""));
        weather.setWeather(pref.getString("weather_desc", ""));
        weather.setPublishTime(pref.getString("publish_time", ""));
        weather.setLoadTime(pref.getString("current_time", ""));
        return weather;
    }

    /**
     * 从服务器上获取最新天气
     *
     * @param weatherCode
     */
    public static void queryWeatherInfoFromServer(String weatherCode, HttpCallbackListener callback) {
        HttpUtil.sendHttpGetRequest(UrlConfig.getWeatherURL(weatherCode), callback);
    }

    /**
     * 获取某个县镇的天气代码
     *
     * @param countyCode
     * @param callback
     */
    public static void queryWeatherCode(String countyCode, HttpCallbackListener callback) {
        HttpUtil.sendHttpGetRequest(UrlConfig.getWeatherServiceCountyURL(countyCode), callback);
    }

    /**
     * 判断是否已选择过城市
     *
     * @return
     */
    public static boolean hasSelectCity() {
        return WeatherApplication.getContext().getSharedPreferences("weather_prefs", Context.MODE_PRIVATE).getBoolean("city_selected", false);
    }
}
