package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/4/9.
 */
public class Future {
    //温度
    @SerializedName("temperature")
    public String temperature;

    //天气情况
    @SerializedName("weather")
    public String weather;

    //天气唯一标识
    @SerializedName("weather_id")
    public More more;

    public class More{
        @SerializedName("fa")
        public String fa;
        @SerializedName("fb")
        public String fb;
    }

    //风向风力
    @SerializedName("wind")
    public String wind;

    //星期
    @SerializedName("week")
    public String week;

}
