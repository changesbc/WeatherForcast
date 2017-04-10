package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/4/9.
 */
//当天气温
public class SK {

    @SerializedName("temp")
    public String temp;

    @SerializedName("humidity")
    public String humidity;  //湿度

    @SerializedName("wind_direction")
    public String windDirection;  //风向

    @SerializedName("wind_strength")
    public String windStrength;  //风力
}
