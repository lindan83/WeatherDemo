package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.config.SqlConfig;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.City;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.County;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装具体的CRUD操作
 */
public class WeatherDBOperation {
    private Context context;
    private static WeatherDBOperation instance;
    private WeatherDataBaseHelper dbHelper;
    private SQLiteDatabase database;

    private WeatherDBOperation(Context context) {
        this.context = context;
        dbHelper = new WeatherDataBaseHelper(this.context, SqlConfig.DB_NAME, null, SqlConfig.DB_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    private WeatherDBOperation() {

    }

    public synchronized static WeatherDBOperation getInstance() {
        if (instance == null) {
            instance = new WeatherDBOperation();
        }
        return instance;
    }

    /**
     * 插入省份数据
     *
     * @param province 省份实体对象
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues row = new ContentValues();
            row.put(SqlConfig.TABLE_COLUMN_NAME_PROVINCE_NAME, province.getProvinceName());
            row.put(SqlConfig.TABLE_COLUMN_NAME_PROVINCE_CODE, province.getProvinceCode());
            database.insert(SqlConfig.TABLE_NAME_PROVINCE, null, row);
        }
    }

    /**
     * 加载省份数据
     *
     * @return 省份列表
     */
    public List<Province> loadProvinces() {
        Cursor cursor = database.query(SqlConfig.TABLE_NAME_PROVINCE, null, null, null, null, null, null);
        if (cursor != null) {
            ArrayList<Province> provinces = new ArrayList<>();
            while (cursor.moveToNext()) {
                Province province = new Province();
                province.setProvinceId(cursor.getInt(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_PROVINCE_ID)));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_PROVINCE_NAME)));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_PROVINCE_CODE)));
                provinces.add(province);
            }
            cursor.close();
            return provinces;
        }
        return null;
    }

    /**
     * 插入城市数据
     *
     * @param city 城市实体
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues row = new ContentValues();
            row.put(SqlConfig.TABLE_COLUMN_NAME_CITY_NAME, city.getCityName());
            row.put(SqlConfig.TABLE_COLUMN_NAME_CITY_CODE, city.getCityCode());
            row.put(SqlConfig.TABLE_COLUMN_NAME_CITY_PID, city.getProvinceId());
            database.insert(SqlConfig.TABLE_NAME_CITY, null, row);
        }
    }

    /**
     * 加载城市数据
     *
     * @return 城市列表
     */
    public List<City> loadCities() {
        Cursor cursor = database.query(SqlConfig.TABLE_NAME_CITY, null, null, null, null, null, null);
        if (cursor != null) {
            ArrayList<City> cities = new ArrayList<>();
            while (cursor.moveToNext()) {
                City city = new City();
                city.setCityId(cursor.getInt(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_CITY_ID)));
                city.setCityName(cursor.getString(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_CITY_NAME)));
                city.setCityCode(cursor.getString(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_CITY_CODE)));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_PROVINCE_ID)));
                cities.add(city);
            }
            cursor.close();
            return cities;
        }
        return null;
    }

    /**
     * 插入县镇数据
     *
     * @param county 县镇实体
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues row = new ContentValues();
            row.put(SqlConfig.TABLE_COLUMN_NAME_COUNTY_NAME, county.getCountyName());
            row.put(SqlConfig.TABLE_COLUMN_NAME_COUNTY_CODE, county.getCountyCode());
            row.put(SqlConfig.TABLE_COLUMN_NAME_COUNTY_CID, county.getCityId());
            database.insert(SqlConfig.TABLE_NAME_COUNTY, null, row);
        }
    }

    /**
     * 加载县镇数据
     *
     * @return 县镇列表
     */
    public List<County> loadCounties() {
        Cursor cursor = database.query(SqlConfig.TABLE_NAME_COUNTY, null, null, null, null, null, null);
        if (cursor != null) {
            ArrayList<County> counties = new ArrayList<>();
            while (cursor.moveToNext()) {
                County county = new County();
                county.setCountyId(cursor.getInt(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_COUNTY_ID)));
                county.setCountyName(cursor.getString(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_COUNTY_NAME)));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_COUNTY_CODE)));
                county.setCityId(cursor.getInt(cursor.getColumnIndex(SqlConfig.TABLE_COLUMN_NAME_COUNTY_CID)));
                counties.add(county);
            }
            cursor.close();
            return counties;
        }
        return null;
    }
}
