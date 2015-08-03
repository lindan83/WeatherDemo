package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.config;

public class UrlConfig {
    public static final String WEATHER_SERVICE_AREA_BASE_URL = "http://www.weather.com.cn/data/list3/";
    public static final String WEATHER_SERVICE_DATA_BASE_URL = "http://www.weather.com.cn/data/cityinfo/";
    public static final String WEATHER_SERVICE_AREA_URL_SUFFIX = ".xml";
    public static final String WEATHER_SERVICE_DATA_URL_SUFFIC = ".html";
    public static final String WEATHER_SERVICE_PROVINCE_URL = WEATHER_SERVICE_AREA_BASE_URL + "city" + WEATHER_SERVICE_AREA_URL_SUFFIX;

    /**
     * 获取指定城市的天气服务URL
     *
     * @param cityCode 城市代码
     * @return
     */
    public static String getWeatherServiceCityURL(String cityCode) {
        return WEATHER_SERVICE_AREA_BASE_URL + "city" + cityCode + WEATHER_SERVICE_AREA_URL_SUFFIX;
    }

    /**
     * 获取指定县镇的天气服务URL
     * @param countyCode
     * @return
     */
    public static String getWeatherServiceCountyURL(String countyCode) {
        return WEATHER_SERVICE_AREA_BASE_URL + "city" + countyCode + WEATHER_SERVICE_AREA_URL_SUFFIX;
    }

    /**
     * 获取指定区县的天气服务URL
     *
     * @param weatherCode 天气代码
     * @return
     */
    public static String getWeatherURL(String weatherCode) {
        return WEATHER_SERVICE_DATA_BASE_URL + weatherCode + WEATHER_SERVICE_DATA_URL_SUFFIC;
    }
}