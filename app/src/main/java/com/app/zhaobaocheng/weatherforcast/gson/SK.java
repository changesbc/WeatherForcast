package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/4/9.
 */
//当前实况天气
public class SK {

    //温度
    @SerializedName("temp")
    public String temp;

    @SerializedName("humidity")
    public String humidity;  //湿度

    @SerializedName("wind_direction")
    public String windDirection;  //风向

    @SerializedName("wind_strength")
    public String windStrength;  //风力

    @SerializedName("time")
    public String time;  //更新的时间
}
