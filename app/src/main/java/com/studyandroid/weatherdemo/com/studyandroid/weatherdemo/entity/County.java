package com.studyandroid.weatherdemo.com.studyandroid.weatherdemo.entity;

public class County {
    private Integer countyId;
    private String countyCode;
    private String countyName;
    private Integer cityId;

    public County() {
    }

    public County(Integer countyId, String countyCode, String countyName, Integer cityId) {
        this.countyId = countyId;
        this.countyCode = countyCode;
        this.countyName = countyName;
        this.cityId = cityId;
    }

    public County(Integer countyId, String countyCode, String countyName) {
        this.countyId = countyId;
        this.countyCode = countyCode;
        this.countyName = countyName;
    }

    public County(String countyCode, String countyName) {
        this.countyCode = countyCode;
        this.countyName = countyName;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return countyCode + "|" + countyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        County county = (County) o;

        if (countyId != null ? !countyId.equals(county.countyId) : county.countyId != null)
            return false;
        if (countyCode != null ? !countyCode.equals(county.countyCode) : county.countyCode != null)
            return false;
        if (countyName != null ? !countyName.equals(county.countyName) : county.countyName != null)
            return false;
        return !(cityId != null ? !cityId.equals(county.cityId) : county.cityId != null);

    }

    @Override
    public int hashCode() {
        int result = countyId != null ? countyId.hashCode() : 0;
        result = 31 * result + (countyCode != null ? countyCode.hashCode() : 0);
        result = 31 * result + (countyName != null ? countyName.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        return result;
    }
}
