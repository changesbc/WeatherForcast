package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/4/9.
 */
public class Today {
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


    @SerializedName("city")
    public String cityName;   //城市名

    @SerializedName("wind")
    public String wind;//风向风力

    @SerializedName("dressing_index")
    public String dressingIndex; //穿衣指数

    @SerializedName("dressing_advice")
    public String dressingAdvice; //穿衣建议

    @SerializedName("uv_index")
    public String uvIndex; //紫外线强度

    @SerializedName("wash_index")
    public String washIndex; //洗车指数

    @SerializedName("travel_index")
    public String travelIndex; //旅游指数

    @SerializedName("exercise_index")
    public String exerciseIndex; //晨练指数

}
