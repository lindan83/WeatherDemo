package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.studyandroid.weatherdemo.R;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.config.UrlConfig;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.database.WeatherDBOperation;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.City;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.County;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity.Province;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util.HttpCallbackListener;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util.HttpUtil;
import com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.util.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaActivity extends BaseActivity {
    private TextView tvTitle;
    private ListView lvChoose;
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter;

    private WeatherDBOperation weatherDBOperation = WeatherDBOperation.getInstance();
    private List<String> adapterData = new ArrayList<>();

    //三个实体集合数据
    private List<Province> provinces = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<County> counties = new ArrayList<>();

    //当前选中级别
    private int currentLevel = 0;

    //选中时保存的三个实体对象
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        initViewAndBind();
    }

    private void initViewAndBind() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvChoose = (ListView) findViewById(R.id.lv_choose);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adapterData);
        lvChoose.setAdapter(adapter);
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    //切换到城市列表并保存当前选中的省份
                    selectedProvince = provinces.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    //切换到县镇列表并保存当前选中的城市
                    selectedCity = cities.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    //保存当前选中的县镇并显示天气
                    selectedCounty = counties.get(position);
                }
            }
        });
        //加载省份数据
        queryProvinces();
    }

    /**
     * 查询省份数据
     */
    private void queryProvinces() {
        provinces = weatherDBOperation.loadProvinces();
        if (provinces != null && provinces.size() > 0) {
            //先从本地数据库缓存查询
            adapterData.clear();
            for (Province p : provinces) {
                adapterData.add(p.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            lvChoose.setSelection(0);
            tvTitle.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            //如果本地缓存数据库中没有，再从服务接口获取
            queryFromServer(null, "province");
        }
    }

    private void queryCities() {
        cities = weatherDBOperation.loadCities(selectedProvince.getProvinceId());
        if (cities != null && cities.size() > 0) {
            //先从本地数据库缓存查询
            adapterData.clear();
            for (City c : cities) {
                adapterData.add(c.getCityName());
            }
            adapter.notifyDataSetChanged();
            lvChoose.setSelection(0);
            tvTitle.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            //如果本地缓存数据库中没有，再从服务接口获取
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    private void queryCounties() {
        counties = weatherDBOperation.loadCounties(selectedCity.getCityId());
        if (counties != null && counties.size() > 0) {
            //先从本地数据库缓存查询
            adapterData.clear();
            for (County c : counties) {
                adapterData.add(c.getCountyName());
            }
            adapter.notifyDataSetChanged();
            lvChoose.setSelection(0);
            tvTitle.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        } else {
            //如果本地缓存数据库中没有，再从服务接口获取
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }

    /**
     * 根据代号和类型从服务器接口上获取最新数据并缓存在本地
     *
     * @param code 代号
     * @param type 类型
     */
    private void queryFromServer(String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            //如果Code非空
            address = UrlConfig.getWeatherServiceCityURL(code);
        } else {
            //如果为空，则查询省份列表
            address = UrlConfig.WEATHER_SERVICE_PROVINCE_URL;
        }
        //显示进度对话框
        showProgressDialog();
        HttpUtil.sendHttpGetRequest(address, new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                if ("province".equals(type)) {
                    provinces = WeatherUtil.parseProvinces(response);
                } else if ("city".equals(type)) {
                    cities = WeatherUtil.parseCities(response, selectedProvince.getProvinceId());
                } else if ("county".equals(type)) {
                    counties = WeatherUtil.parseCounties(response, selectedCity.getCityId());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //数据获取完毕，关闭对话框
                        closeProgressDialog();
                        if ("province".equals(type)) {
                            queryProvinces();
                        } else if ("city".equals(type)) {
                            queryCities();
                        } else if ("county".equals(type)) {
                            queryCounties();
                        }
                    }
                });
            }

            @Override
            public void onResponseError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载数据失败，请重试", Toast.LENGTH_SHORT).
                                show();
                    }
                });
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        //捕获Back按键
        if (currentLevel == LEVEL_COUNTY) {
            //返回城市列表
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            //返回省份列表
            queryProvinces();
        } else {
            //关闭
            finish();
        }
    }
}
