package com.app.zhaobaocheng.weatherforcast;

import com.thinkland.sdk.android.JuheSDKInitializer;

import org.litepal.LitePalApplication;

/**
 * Created by ZhaoBaocheng on 2017/4/9.
 */
public class MyApplication extends LitePalApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        JuheSDKInitializer.initialize(getApplicationContext());
    }
}
