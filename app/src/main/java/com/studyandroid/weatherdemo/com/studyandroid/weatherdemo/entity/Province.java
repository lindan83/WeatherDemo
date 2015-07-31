package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity;

import java.io.Serializable;

public class Province implements Serializable {
    private Integer provinceId;
    private String provinceCode;
    private String provinceName;

    public Province() {

    }

    public Province(Integer provinceId, String provinceCode, String provinceName) {
        this.provinceId = provinceId;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
    }

    public Province(String provinceCode, String provinceName) {
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return provinceCode + "|" + provinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Province province = (Province) o;

        if (provinceId != null ? !provinceId.equals(province.provinceId) : province.provinceId != null)
            return false;
        if (provinceCode != null ? !provinceCode.equals(province.provinceCode) : province.provinceCode != null)
            return false;
        return !(provinceName != null ? !provinceName.equals(province.provinceName) : province.provinceName != null);

    }

    @Override
    public int hashCode() {
        int result = provinceId != null ? provinceId.hashCode() : 0;
        result = 31 * result + (provinceCode != null ? provinceCode.hashCode() : 0);
        result = 31 * result + (provinceName != null ? provinceName.hashCode() : 0);
        return result;
    }
}
