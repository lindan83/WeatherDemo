package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studyandroid.weatherdemo.R;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Weather;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util.HttpCallbackListener;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util.WeatherUtil;

public class WeatherActivity extends BaseActivity {
    private LinearLayout layoutWeatherInfo;//用于显示天气信息
    private TextView tvCityName;//用于显示城市名称
    private TextView tvPublish;//用于显示天气发布时间
    private TextView tvCurrentDate;//当前日期
    private TextView tvWeatherDesc;//天气描述
    private TextView tvTemp1, tvTemp2;//天气

    private Button btnSwitchCity, btnRefreshWeather;//切换城市及刷新天气

    private String countyCode;//县镇代号
    private String weatherCode;//天气代码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();

        countyCode = getIntent().getStringExtra("county_code");
        if (TextUtils.isEmpty(countyCode)) {
            //没有县镇代号则显示本地天气
            showWeatherInfo();
        } else {
            //存在县镇代号则查询天气
            tvPublish.setText("同步中");
            layoutWeatherInfo.setVisibility(View.INVISIBLE);
            tvCityName.setVisibility(View.INVISIBLE);
            queryWeatherInfo(countyCode);
        }
    }

    private void initView() {
        layoutWeatherInfo = (LinearLayout) findViewById(R.id.layout_weather_info);
        tvCityName = (TextView) findViewById(R.id.tv_city_name);
        tvPublish = (TextView) findViewById(R.id.tv_publish);
        tvCurrentDate = (TextView) findViewById(R.id.tv_current_date);
        tvWeatherDesc = (TextView) findViewById(R.id.tv_weather_desc);
        tvTemp1 = (TextView) findViewById(R.id.tv_temp1);
        tvTemp2 = (TextView) findViewById(R.id.tv_temp2);
        btnSwitchCity = (Button) findViewById(R.id.btn_switch_city);
        btnRefreshWeather = (Button) findViewById(R.id.btn_refresh);
        btnSwitchCity.setOnClickListener(clickListener);
        btnRefreshWeather.setOnClickListener(clickListener);
    }

    public static void show(Context from, String countyCode) {
        Intent intent = new Intent(from, WeatherActivity.class);
        intent.putExtra("county_code", countyCode);
        from.startActivity(intent);
    }

    /**
     * @param countyCode
     */
    private void queryWeatherInfo(String countyCode) {
        WeatherUtil.queryWeatherCode(countyCode, new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    weatherCode = response.split("\\|")[1];
                    WeatherUtil.queryWeatherInfoFromServer(weatherCode, new HttpCallbackListener() {
                        @Override
                        public void onResponseSuccess(String response) {
                            WeatherUtil.parseWeatherInfo(weatherCode, response);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showWeatherInfo();
                                }
                            });
                        }

                        @Override
                        public void onResponseError(Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WeatherActivity.this, "查询不到天气信息", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onResponseError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "查询不到天气信息", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示天气信息
     */
    private void showWeatherInfo() {
        Weather weather = WeatherUtil.loadWeather();
        if (!TextUtils.isEmpty(weather.getCity())) {
            tvCityName.setText(weather.getCity());
            tvPublish.setText(weather.getPublishTime() + "发布");
            tvCurrentDate.setText(weather.getLoadTime());
            tvTemp1.setText(weather.getTemp1());
            tvTemp2.setText(weather.getTemp2());
            tvWeatherDesc.setText(weather.getWeather());
            layoutWeatherInfo.setVisibility(View.VISIBLE);
            tvCityName.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_switch_city:
                    //切换城市
                    ChooseAreaActivity.show(WeatherActivity.this, true);
                    finish();
                    break;
                case R.id.btn_refresh:
                    //立即刷新
                    tvPublish.setText("同步中");
                    layoutWeatherInfo.setVisibility(View.INVISIBLE);
                    tvCityName.setVisibility(View.INVISIBLE);
                    queryWeatherInfo(countyCode);
                    break;
            }
        }
    };
}
