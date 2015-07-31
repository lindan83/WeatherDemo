package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.config;

public class SqlConfig {
    public static final String DB_NAME = "weather_db.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME_PROVINCE = "province";
    public static final String TABLE_NAME_CITY = "city";
    public static final String TABLE_NAME_COUNTY = "county";

    public static final String TABLE_COLUMN_NAME_PROVINCE_ID = "id";
    public static final String TABLE_COLUMN_NAME_PROVINCE_NAME = "province_name";
    public static final String TABLE_COLUMN_NAME_PROVINCE_CODE = "province_code";

    public static final String TABLE_COLUMN_NAME_CITY_ID = "id";
    public static final String TABLE_COLUMN_NAME_CITY_NAME = "city_name";
    public static final String TABLE_COLUMN_NAME_CITY_CODE = "city_code";
    public static final String TABLE_COLUMN_NAME_CITY_PID = "province_id";

    public static final String TABLE_COLUMN_NAME_COUNTY_ID = "id";
    public static final String TABLE_COLUMN_NAME_COUNTY_NAME = "county_name";
    public static final String TABLE_COLUMN_NAME_COUNTY_CODE = "county_code";
    public static final String TABLE_COLUMN_NAME_COUNTY_CID = "city_id";

    public static final String CREATE_TABLE_PROVINCE = "create table province(id integer primary key autoincrement,province_name text not null,province_code text not null)";
    public static final String CREATE_TABLE_CITY = "create table city(id integer primary key autoincrement,city_name text not null,city_code text not null,province_id integer)";
    public static final String CREATE_TABLE_COUNTY = "create table county(id integer primary key autoincrement,county_name text not null,county_code text not null,city_id integer)";

}
