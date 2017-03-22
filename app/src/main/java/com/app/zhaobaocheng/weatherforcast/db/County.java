package com.app.zhaobaocheng.weatherforcast.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ZhaoBaocheng on 2017/3/21.
 */
public class County extends DataSupport{
    private int id;
    private String countyName;  //县的名字
    private String weatherId;  //记录县所对应的天气的id
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
