package com.app.zhaobaocheng.weatherforcast.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.app.zhaobaocheng.weatherforcast.gson.Weather;
import com.app.zhaobaocheng.weatherforcast.gson.WeatherBean;
import com.app.zhaobaocheng.weatherforcast.util.HttpUtil;
import com.app.zhaobaocheng.weatherforcast.util.Utility;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        updateWeather();
        updateBingPic();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour=8*60*60*1000;//8小时的毫秒数
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气信息
     */
    private void updateWeather(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=preferences.getString("weather",null);
        if(weatherString!=null){
            //有缓存时直接解析天气数据
//            Weather weather= Utility.handleWeatherResponse(weatherString);
//            Weather weather= Utility.handleWeatherResponse(weatherString);
            Weather weather = null;
            try {
                JSONObject jsonObject=new JSONObject(weatherString);
                JSONObject result=jsonObject.getJSONObject("result");
                Gson gson=new Gson();
                weather=gson.fromJson(result.toString(),Weather.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            String weatherId=weather.basic.weatherId;
            String weatherId=weather.today.cityName;

//            String weatherUrl="http://guolin.tech/api/weather?cityid="+
//                    weatherId+"&key=6c10b738f9534d0cb3f9a04025b9c857";
            String weatherUrl="http://v.juhe.cn/weather/index?format=2&cityname="+
                    weatherId+ "&key=c8f6950b1b4e5c10ff6a6e8fd6ed8f6a";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body().string();
//                    Weather weather=Utility.handleWeatherResponse(responseText);
//                    Weather weather=Utility.handleWeatherResponse(responseText);
                    Weather weather = null;
                    try {
                        JSONObject jsonObject=new JSONObject(responseText);
                        JSONObject result=jsonObject.getJSONObject("result");
                        Gson gson=new Gson();
                        weather=gson.fromJson(result.toString(),Weather.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(weather !=null){
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",responseText);
                        editor.apply();
                    }
                    if(weather !=null){
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",responseText);
                        editor.apply();
                    }

                }
            });
        }
    }

    /**
     * 更新必应每日一图
     */
    private void updateBingPic(){
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
            }
        });
    }
}
