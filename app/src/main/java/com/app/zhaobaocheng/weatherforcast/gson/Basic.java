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
    public String cityName;   //城市名

    @SerializedName("id")
    public String weatherId;   //城市对应的天气id

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;  //天气的更新时间
    }
}
