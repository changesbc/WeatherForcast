package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */

/**
 * "daily_forcast":[
 * {
 *     "date":"",
 *     "cond":{
 *         "txt_d":""
 *     },
 *     "tmp":{
 *         "max":"",
 *         "min":""
 *     }
 * },
 * {
 *     "date":"",
 *     "cond":{
 *         "txt_d":""
 *     },
 *     "tmp":{
 *         "max":"",
 *         "min":""
 *     }
 * },
 * ...
 * ]
 */
public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;
        public String min;
    }

    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
