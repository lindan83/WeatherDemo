package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static SimpleDateFormat formatter = new SimpleDateFormat();
    public static final String FORMAT_FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss S";
    public static final String FORMAT_LONG_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_SHORT_DATE = "yy/MM/dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_TIME_HOUR = "HH";
    public static final String FORMAT_TIME_MINUTE = "mm";
    public static final String FORMAT_TIME_SECOND = "ss";

    private DateTimeUtil() {
    }

    public static String getDateTime(String pattern) {
        try {
            formatter.applyPattern(pattern);
            return formatter.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }
}
