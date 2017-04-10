package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */
public class Weather {
    public String status;
    public Basic basic;  //成功返回ok
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    public Today today;
    public SK sk;

//    @SerializedName("daily_forecast")
//    public List<Forecast> forecastList;

    @SerializedName("future")
    public List<Future> futureList;
}
