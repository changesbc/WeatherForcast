package com.app.zhaobaocheng.weatherforcast.util;

import android.text.TextUtils;

import com.app.zhaobaocheng.weatherforcast.db.City;
import com.app.zhaobaocheng.weatherforcast.db.County;
import com.app.zhaobaocheng.weatherforcast.db.Province;
import com.app.zhaobaocheng.weatherforcast.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZhaoBaocheng on 2017/3/21.
 */
public class Utility {
    /***
     * 解析和处理服务器返回的省级数据
     */

    public static boolean handProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces=new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName((provinceObject.getString("name")));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities=new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties=new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject=allCounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
//            Log.d("jsonObject.length()", String.valueOf(jsonObject.length()));
            JSONObject result=jsonObject.getJSONObject("result");

            JSONArray futureArray=result.getJSONArray("future");
//            Log.d("future", String.valueOf(futureArray));

            JSONObject today=result.getJSONObject("today");
//            Log.d("today", String.valueOf(today));
            return new Gson().fromJson(result.toString(),Weather.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        try {
//            JSONObject jsonObject=new JSONObject(response);
////            if(jsonObject.getString("resultcode")=="200" && jsonObject.getInt("error_code")==0){
//////                JSONArray resultArray = jsonObject.getJSONArray("result");
////                JSONObject json=jsonObject.getJSONObject("result");
////                Log.d("json", String.valueOf(json));
////            }
//            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
//            String weatherContent=jsonArray.getJSONObject(0).toString();
//            Log.d("HeWeather",weatherContent);
////            return new Gson().fromJson(weatherContent,Weather.class);
//            return new Gson().fromJson(response,Weather.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
