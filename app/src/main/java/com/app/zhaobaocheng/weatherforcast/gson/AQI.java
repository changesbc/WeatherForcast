package com.app.zhaobaocheng.weatherforcast.gson;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */

/**
 * "aqi":{
 *     "city":{
 *     "aqi":"",
 *     "pm":""
 *     }
 *
 * }
 */
public class AQI {

    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
