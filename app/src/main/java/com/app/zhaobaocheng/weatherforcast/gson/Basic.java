package com.app.zhaobaocheng.weatherforcast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */

/***
 * "basic":{
 *     "city":"",
 *     "id":"",
 *     "update":{
 *         "loc":""
 *     }
 * }
 */
public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
