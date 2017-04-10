package com.app.zhaobaocheng.weatherforcast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.zhaobaocheng.weatherforcast.util.HttpUtil;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ZhaoBaocheng on 2017/3/26.
 */
public class SplashActivity extends AppCompatActivity{
    private ImageView splashImg;
    private RelativeLayout rlRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(1);
        //必应闪屏图片
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_splash);
//        loadJuheData();
        loadBingPic();  //每日刷新
        initView();
        animationSet();
    }

    //加载聚合数据
    public void loadJuheData(){
        String weatherUrl="http://v.juhe.cn/weather/index?format=2&cityname=苏州"+
                "&key=c8f6950b1b4e5c10ff6a6e8fd6ed8f6a";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SplashActivity.this,"获取天气信息失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                Log.d("聚合数据：",responseText);
                try {
                    JSONObject jsonObject=new JSONObject(responseText);
                    Log.d("jsonObject.length()", String.valueOf(jsonObject.length()));
                    JSONObject result=jsonObject.getJSONObject("result");

                    JSONArray futureArray=result.getJSONArray("future");
                    Log.d("future", String.valueOf(futureArray));

                    JSONObject today=result.getJSONObject("today");
                    Log.d("today", String.valueOf(today));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 数据初始化
     */
    private void initView() {
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        splashImg= (ImageView) findViewById(R.id.splash_bing);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String bingSplash=preferences.getString("bing_splash",null);
        if(bingSplash!=null){
            Glide.with(this).load(bingSplash).into(splashImg);
        }else{
            //TODO 加载必应每日一图
            loadBingPic();
        }
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic(){
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingSplash=response.body().string();
                SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                editor.putString("bing_splash",bingSplash);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(SplashActivity.this).load(bingSplash).into(splashImg);
                    }
                });
            }
        });
    }

    /**
     * 动画
     */
    private void animationSet() {
        //旋转动画
        RotateAnimation rotateAnimation=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);  //动画持续时间
        rotateAnimation.setFillAfter(true);

        //缩放动画
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true); //保持动画结束状态

        //渐变动画
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        //动画集合
        AnimationSet set=new AnimationSet(true);
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        //启动动画
        rlRoot.startAnimation(set);

        //动画监听
        set.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，跳转页面
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();  //结束当前页面
            }

        });
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
