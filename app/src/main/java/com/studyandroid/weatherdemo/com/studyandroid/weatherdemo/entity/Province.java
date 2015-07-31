package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity;

import java.io.Serializable;

public class Province implements Serializable {
    private String provinceId;
    private String provinceName;

    public Province() {

    }

    public Province(String provinceId, String provinceName) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return provinceId + "|" + provinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Province province = (Province) o;

        if (provinceId != null ? !provinceId.equals(province.provinceId) : province.provinceId != null)
            return false;
        return !(provinceName != null ? !provinceName.equals(province.provinceName) : province.provinceName != null);

    }

    @Override
    public int hashCode() {
        int result = provinceId != null ? provinceId.hashCode() : 0;
        result = 31 * result + (provinceName != null ? provinceName.hashCode() : 0);
        return result;
    }
}
