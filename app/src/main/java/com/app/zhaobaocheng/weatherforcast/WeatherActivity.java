package com.app.zhaobaocheng.weatherforcast;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.zhaobaocheng.weatherforcast.gson.Forecast;
import com.app.zhaobaocheng.weatherforcast.gson.Future;
import com.app.zhaobaocheng.weatherforcast.gson.FutureWeatherBean;
import com.app.zhaobaocheng.weatherforcast.gson.Weather;
import com.app.zhaobaocheng.weatherforcast.gson.WeatherBean;
import com.app.zhaobaocheng.weatherforcast.service.AutoUpdateService;
import com.app.zhaobaocheng.weatherforcast.util.HttpUtil;
import com.app.zhaobaocheng.weatherforcast.util.Utility;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ZhaoBaocheng on 2017/3/22.
 */
public class WeatherActivity extends AppCompatActivity{
    private ScrollView weatherLayout;
    private TextView titleCity;
//    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;  //加载必应图片
    public SwipeRefreshLayout swipeRefresh; //自动刷新
    public DrawerLayout drawerLayout;  //实现滑动菜单
    private Button navButton;
//    private String weatherId;
    private String mWeatherId;
    private ImageButton btnShare;

    private ImageView mNowWeather;// 现在天气
    private ImageView mNextMondayWeather;// 下周一天气
    private ImageView mNextTuesdayWeather;// 下周二天气
    private ImageView mNextWednesdayWeather;// 下周三天气
    private ImageView mNextThursdayWeather;// 下周四天气
    private ImageView mNextFridayWeather;// 下周五天气
    private ImageView mNextSaturdayWeather;// 下周六天气

    private TextView mTodayTemp; //今天的温度
    private TextView mNextMondayTemp; //下周一的温度
    private TextView mNextTuesdayTemp; //下周二的温度
    private TextView mNextWednesdayTemp; //下周三的温度
    private TextView mNextThursdayTemp; //下周四的温度
    private TextView mNextFridayTemp; //下周五的温度
    private TextView mNextSaturdayTemp; //下周六的温度

    private TextView mToday; //今天
    private TextView mNextMonday; //下周一
    private TextView mNextTuesday; //下周二
    private TextView mNextWednesday; //下周三
    private TextView mNextThursday; //下周四
    private TextView mNextFriday; //下周五
    private TextView mNextSaturday; //下周六

    private TextView mHumidityText; //湿度
    private TextView mWindText; //风向风力
    private TextView mDressingIndexText; //穿衣指数
    private TextView mDressingAdviceText; //穿衣建议
    private TextView mUvIndexText; //紫外线强度
    private TextView mWashIndexText; //洗车指数
    private TextView mTravelIndexText; //旅游指数
    private TextView mExerciseIndexText; //晨练指数

    private TextView mWindDirection;  //风向
    private TextView mWindStrength;   //风力


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(1);  //隐藏标题栏
        //必应图片
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);

        //初始化控件
        Init();
    }

    /**
     * 初始化控件
     */
    private void Init() {
        mWindDirection= (TextView) findViewById(R.id.tv_wind_direction);
        mWindStrength= (TextView) findViewById(R.id.tv_wind_strength);

        mNowWeather= (ImageView) findViewById(R.id.iv_today);
        mNextMondayWeather= (ImageView) findViewById(R.id.iv_next_monday);
        mNextTuesdayWeather= (ImageView) findViewById(R.id.iv_next_tuesday);
        mNextWednesdayWeather= (ImageView) findViewById(R.id.iv_next_wednesday);
        mNextThursdayWeather= (ImageView) findViewById(R.id.iv_next_thursday);
        mNextFridayWeather= (ImageView) findViewById(R.id.iv_next_friday);
        mNextSaturdayWeather= (ImageView) findViewById(R.id.iv_next_saturday);

        mTodayTemp= (TextView) findViewById(R.id.tv_today_temp);
        mNextMondayTemp= (TextView) findViewById(R.id.tv_next_mondey_temp);
        mNextTuesdayTemp= (TextView) findViewById(R.id.tv_next_tuesday_temp);
        mNextWednesdayTemp= (TextView) findViewById(R.id.tv_next_wednesday_temp);
        mNextThursdayTemp= (TextView) findViewById(R.id.tv_next_thursday_temp);
        mNextFridayTemp= (TextView) findViewById(R.id.tv_next_friday_temp);
        mNextSaturdayTemp= (TextView) findViewById(R.id.tv_next_saturday_temp);

        mToday= (TextView) findViewById(R.id.tv_today);
        mNextMonday= (TextView) findViewById(R.id.tv_next_monday);
        mNextTuesday= (TextView) findViewById(R.id.tv_next_tuesday);
        mNextWednesday= (TextView) findViewById(R.id.tv_next_wednesday);
        mNextThursday= (TextView) findViewById(R.id.tv_next_thursday);
        mNextFriday= (TextView) findViewById(R.id.tv_next_friday);
        mNextSaturday= (TextView) findViewById(R.id.tv_next_saturday);

        mHumidityText= (TextView) findViewById(R.id.tv_humidity);
        mWindText= (TextView) findViewById(R.id.tv_wind);
        mDressingIndexText= (TextView) findViewById(R.id.tv_dressing_index);
        mDressingAdviceText= (TextView) findViewById(R.id.tv_dressing_advice);
        mUvIndexText= (TextView) findViewById(R.id.tv_uv_index);
        mWashIndexText= (TextView) findViewById(R.id.tv_wash_index);
        mTravelIndexText= (TextView) findViewById(R.id.tv_travel_index);
        mExerciseIndexText= (TextView) findViewById(R.id.tv_exercise_index);

        weatherLayout= (ScrollView) findViewById(R.id.weather_layout);
        titleCity= (TextView) findViewById(R.id.title_city);
//        titleUpdateTime= (TextView) findViewById(R.id.title_update_time);
        degreeText= (TextView) findViewById(R.id.degree_text);
        weatherInfoText= (TextView) findViewById(R.id.weather_info_text);
        forecastLayout= (LinearLayout) findViewById(R.id.forecast_layout);

//        comfortText= (TextView) findViewById(R.id.comfort_text);
//        carWashText= (TextView) findViewById(R.id.car_wash_text);
//        sportText= (TextView) findViewById(R.id.sport_text);
        bingPicImg=(ImageView)findViewById(R.id.bing_pic_img);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);
        btnShare= (ImageButton) findViewById(R.id.btn_share);  //分享
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 分享功能
                showShare();
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=preferences.getString("weather",null);
//        final String weatherId;
        Log.d("weatherString",weatherString);
        if(weatherString!=null){

            //有缓存是直接解析天气数据
//            Weather weather= Utility.handleWeatherResponse(weatherString);
//            mWeatherId=weather.basic.weatherId;
//            mWeatherId=weather.today.cityName;
            Weather weather = null;
            try {
                JSONObject jsonObject=new JSONObject(weatherString);
                JSONObject result=jsonObject.getJSONObject("result");
                Gson gson=new Gson();
                weather=gson.fromJson(result.toString(),Weather.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mWeatherId=weather.today.cityName;
            //TODO
            showWeatherInfo(weather);
        }else{
            //无缓存是去服务器查询天气
            mWeatherId=getIntent().getStringExtra("cityName");
            weatherLayout.setVisibility(View.INVISIBLE);
            //TODO
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

        String bingPic=preferences.getString("bing_pic",null);
        if(bingPic!=null){
            Glide.with(this).load(bingPic).into(bingPicImg);
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

                final String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String cityName){
//        Log.d("weatherId",cityName);
//        http://v.juhe.cn/weather/index?format=2&cityname=%E8%8B%8F%E5%B7%9E&key=c8f6950b1b4e5c10ff6a6e8fd6ed8f6a
//        String weatherUrl="http://guolin.tech/api/weather?cityid="+
//                weatherId+"&key=6c10b738f9534d0cb3f9a04025b9c857";
        String weatherUrl="http://v.juhe.cn/weather/index?format=2&cityname="+
                cityName+ "&key=c8f6950b1b4e5c10ff6a6e8fd6ed8f6a";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText=response.body().string();
//                Log.d("聚合数据：",responseText);
//                final Weather weather=Utility.handleWeatherResponse(responseText);
                final Weather weather;
//                Log.d("weather：", String.valueOf(weather));
//                Log.d("weather", weather.today.weather);
                try {
                    JSONObject jsonObject=new JSONObject(responseText);
                    JSONObject result=jsonObject.getJSONObject("result");
                    Gson gson=new Gson();
                    weather=gson.fromJson(result.toString(),Weather.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(weather != null){
                                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                                editor.putString("weather",responseText);
                                editor.apply();
//                                mWeatherId=weather.basic.weatherId;
                                mWeatherId=weather.today.cityName;
                                //TODO 显示天气信息
                                showWeatherInfo(weather);
                            }else{
                                Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        loadBingPic();
    }

    /**
     * 处理并展示Weather实体类中的数据
     * @param weather
     */
    private void showWeatherInfo(Weather weather){
        // && "ok".equals(weather.status)
        Log.d("信息", String.valueOf(weather));
        if(weather !=null){   //为null
            String cityName=weather.today.cityName;
            Log.d("cityName",cityName);
//            String cityName=weather.today.cityName;
//            String updateTime=weather.basic.update.updateTime.split(" ")[1];
//            Log.d("时间：",updateTime);
//            String degree=weather.now.temperature+"℃";
//            String weatherInfo=weather.now.more.info;
//            String degree=weather.today.temperature+"℃";
            String degree=weather.sk.temp+"℃";
            String weatherInfo=weather.today.weather;
            titleCity.setText(cityName);
//            titleUpdateTime.setText(updateTime);
            degreeText.setText(degree);
            weatherInfoText.setText(weatherInfo);

            //实时的风向风力
            mWindDirection.setText(weather.sk.windDirection);
            mWindStrength.setText(weather.sk.windStrength);

            //TODO 白天和黑夜
            mNowWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(0).more.fa, "drawable", getPackageName()));
                mNextMondayWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(1).more.fa, "drawable", getPackageName()));
                mNextTuesdayWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(2).more.fa, "drawable", getPackageName()));
                mNextWednesdayWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(3).more.fa, "drawable", getPackageName()));
                mNextThursdayWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(4).more.fa, "drawable", getPackageName()));
                mNextFridayWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(5).more.fa, "drawable", getPackageName()));
                mNextSaturdayWeather.setImageResource(getResources().getIdentifier("d" +
                        weather.futureList.get(6).more.fa, "drawable", getPackageName()));

            mTodayTemp.setText(weather.futureList.get(0).temperature);
            mNextMondayTemp.setText(weather.futureList.get(1).temperature);
            mNextTuesdayTemp.setText(weather.futureList.get(2).temperature);
            mNextWednesdayTemp.setText(weather.futureList.get(3).temperature);
            mNextThursdayTemp.setText(weather.futureList.get(4).temperature);
            mNextFridayTemp.setText(weather.futureList.get(5).temperature);
            mNextSaturdayTemp.setText(weather.futureList.get(6).temperature);

            mToday.setText(weather.futureList.get(0).week);
            mNextMonday.setText(weather.futureList.get(1).week);
            mNextTuesday.setText(weather.futureList.get(2).week);
            mNextWednesday.setText(weather.futureList.get(3).week);
            mNextThursday.setText(weather.futureList.get(4).week);
            mNextFriday.setText(weather.futureList.get(5).week);
            mNextSaturday.setText(weather.futureList.get(6).week);

            mHumidityText.setText(weather.sk.humidity);  //湿度
            mWindText.setText(weather.today.wind);
            mDressingIndexText.setText(weather.today.dressingIndex);
            mDressingAdviceText.setText(weather.today.dressingAdvice);
            mUvIndexText.setText(weather.today.uvIndex);
            mWashIndexText.setText(weather.today.washIndex);
            mTravelIndexText.setText(weather.today.travelIndex);
            mExerciseIndexText.setText(weather.today.exerciseIndex);

//            for(Future future : weather.futureList){
//                Log.d("集合：", future.week);
//                mTodayTemp.setText(future.temperature);
//                mNextMondayTemp.setText(future.temperature);
//                mNextTuesdayTemp.setText(future.temperature);
//                mNextWednesdayTemp.setText(future.temperature);
//                mNextThursdayTemp.setText(future.temperature);
//                mNextFridayTemp.setText(future.temperature);
//                mNextSaturdayTemp.setText(future.temperature);
//
//                mToday.setText(future.week);
//                mNextMonday.setText(future.week);
//                mNextTuesday.setText(future.week);
//                mNextWednesday.setText(future.week);
//                mNextThursday.setText(future.week);
//                mNextFriday.setText(future.week);
//                mNextSaturday.setText(future.week);
//
////                mNowWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
////                mNextMondayWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
////                mNextTuesdayWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
////                mNextWednesdayWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
////                mNextThursdayWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
////                mNextFridayWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
////                mNextSaturdayWeather.setImageResource(getResources().getIdentifier("d" +
////                        future.more.fa, "drawable", "com.juhe.weather"));
//
//
//
//            }
            
//            forecastLayout.removeAllViews();
//            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,
//                    forecastLayout,false);
//
//            forecastLayout.addView(view);
//            for(Forecast forecast : weather.forecastList){
//                View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,
//                        forecastLayout,false);
//                TextView dateText= (TextView) view.findViewById(R.id.date_text);
//                TextView infoText= (TextView) view.findViewById(R.id.info_text);
//                TextView maxText= (TextView) view.findViewById(R.id.max_text);
//                TextView minText= (TextView) view.findViewById(R.id.min_text);
//                dateText.setText(forecast.date);
//                infoText.setText(forecast.more.info);
//                maxText.setText(forecast.temperature.max);
//                minText.setText(forecast.temperature.min);
//                forecastLayout.addView(view);
//            }
//            if(weather.aqi!=null){
//                aqiText.setText(weather.aqi.city.aqi);
//                pm25Text.setText(weather.aqi.city.pm25);
//            }
//            String comfort="舒适度："+weather.suggestion.comfort.info;
//            String carWash="洗车指数："+weather.suggestion.carWash.info;
//            String sport="运动建议："+weather.suggestion.sport.info;
//            comfortText.setText(comfort);
//            carWashText.setText(carWash);
//            sportText.setText(sport);
//            List<FutureWeatherBean> futureList = weather.getFutureList();
//            Log.d("天气：", String.valueOf(futureList.size()));
            weatherLayout.setVisibility(View.VISIBLE);
            Intent intent=new Intent(this, AutoUpdateService.class);
            startService(intent);
        }else{
            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 分享
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("炫酷天气");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这是一款超酷的天气APP，赶快来下载试试吧！！");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

}
