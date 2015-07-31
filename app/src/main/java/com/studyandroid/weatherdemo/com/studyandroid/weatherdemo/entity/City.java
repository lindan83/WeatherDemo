package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity;

import java.io.Serializable;

public class City implements Serializable {
    private Integer cityId;
    private String cityCode;
    private String cityName;
    private Integer provinceId;

    public City() {

    }

    public City(Integer cityId, String cityCode, String cityName, Integer provinceId) {
        this.cityId = cityId;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.provinceId = provinceId;
    }

    public City(Integer cityId, String cityCode, String cityName) {
        this.cityId = cityId;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public City(String cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return cityCode + "|" + cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (cityId != null ? !cityId.equals(city.cityId) : city.cityId != null) return false;
        if (cityCode != null ? !cityCode.equals(city.cityCode) : city.cityCode != null)
            return false;
        if (cityName != null ? !cityName.equals(city.cityName) : city.cityName != null)
            return false;
        return !(provinceId != null ? !provinceId.equals(city.provinceId) : city.provinceId != null);

    }

    @Override
    public int hashCode() {
        int result = cityId != null ? cityId.hashCode() : 0;
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (provinceId != null ? provinceId.hashCode() : 0);
        return result;
    }
}
