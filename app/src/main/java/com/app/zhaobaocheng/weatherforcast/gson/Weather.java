package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */
public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forcast")
    public List<Forecast> forecastList;
}
