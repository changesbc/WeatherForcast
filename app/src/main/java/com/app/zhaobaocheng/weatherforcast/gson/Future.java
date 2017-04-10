package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/4/9.
 */
public class Future {
    @SerializedName("temperature")
    public String temperature;

    @SerializedName("weather")
    public String weather;

    @SerializedName("weather_id")
    public More more;

    public class More{
        @SerializedName("fa")
        public String fa;
        @SerializedName("fb")
        public String fb;
    }

    @SerializedName("wind")
    public String wind;

    @SerializedName("week")
    public String week;

}
