package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.config.SqlConfig;

public class WeatherDataBaseHelper extends SQLiteOpenHelper {
    public WeatherDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlConfig.CREATE_TABLE_PROVINCE);
        db.execSQL(SqlConfig.CREATE_TABLE_CITY);
        db.execSQL(SqlConfig.CREATE_TABLE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
