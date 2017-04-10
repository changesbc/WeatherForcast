package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */
public class Weather {
    public Today today;
    public SK sk;

    @SerializedName("future")
    public List<Future> futureList;
}
