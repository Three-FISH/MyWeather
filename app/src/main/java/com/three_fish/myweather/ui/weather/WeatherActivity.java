package com.three_fish.myweather.ui.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.three_fish.myweather.Gson.Forecast;
import com.three_fish.myweather.Gson.Weather;
import com.three_fish.myweather.R;
import com.three_fish.myweather.Util.Utility;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    public WeatherViewModel weatherViewModel;
    String weatherId;
    public DrawerLayout drawerLayout;
    TextView titleCity;
    TextView titleUpdateTime;
    TextView degreeText;
    TextView weatherInfoText;
    LinearLayout forecastLayout;
    TextView aqiText;
    TextView pm25Text;
    TextView comfortText;
    TextView carWashText;
    TextView sportText;
    public SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences spf;
    TextView navChange;
    ImageView bingImageView;
    String bingImgPic;

    private Observer<String> toastTextOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Toast.makeText(WeatherActivity.this,s,Toast.LENGTH_LONG).show();
        }
    };
    private Observer<String> weatherSpOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            bingImgPic = s;
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
            editor.putString("weather",s);
            editor.apply();
        }
    };
    private Observer<String> bingPicOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {

            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
            editor.putString("bing_pic",s);
            editor.apply();
        }
    };
    private Observer<Boolean> swipeRefreshOb = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if(aBoolean){
                swipeRefreshLayout.setRefreshing(true);
            }else {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    private Observer<String> cityTitleOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            titleCity.setText(s);
        }
    };

    private Observer<String> updateTimeOB = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            titleUpdateTime.setText(s);
        }
    };
    private Observer<String> degreeTextOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            degreeText.setText(s);
        }
    };
    private Observer<String> weatherInfoOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            weatherInfoText.setText(s);
        }
    };
    private Observer<ArrayList<Forecast>> forecastDataOb = new Observer<ArrayList<Forecast>>() {
        @Override
        public void onChanged(ArrayList<Forecast> forecasts) {
            forecastLayout.removeAllViews();
            for (Forecast forecast : forecasts){
                View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item,forecastLayout,false);
                TextView dataText = view.findViewById(R.id.data_text);
                TextView infoText = view.findViewById(R.id.info_text);
                TextView minText = view.findViewById(R.id.min_text);
                TextView maxText = view.findViewById(R.id.max_text);
                dataText.setText(forecast.getDate());
                infoText.setText(forecast.getMore().getInfo());
                minText.setText(forecast.getTmperature().getMin());
                maxText.setText(forecast.getTmperature().getMax());
                forecastLayout.addView(view);
            }
        }
    };
    private Observer<ArrayList<String>> aqiDataOb = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> strings) {
            aqiText.setText(strings.get(0));
            pm25Text.setText(strings.get(1));
        }
    };
    private Observer<ArrayList<String>> suggestionOb = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> strings) {
            comfortText.setText(strings.get(0));
            carWashText.setText(strings.get(1));
            sportText.setText(strings.get(2));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiText= findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        navChange = findViewById(R.id.nav_button);
        drawerLayout = findViewById(R.id.drawer_layout);
        bingImageView = findViewById(R.id.bing_pic_img);


        spf = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = spf.getString("weather",null);
        String bingImgString = spf.getString("bing_pic",null);



        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.toastText.observe(this,toastTextOb);
        weatherViewModel.weatherSp.observe(this,weatherSpOb);
        weatherViewModel.cityTitle.observe(this,cityTitleOb);
        weatherViewModel.updateTime.observe(this,updateTimeOB);
        weatherViewModel.degreeText.observe(this,degreeTextOb);
        weatherViewModel.weatherInfoText.observe(this,weatherInfoOb);
        weatherViewModel.forecastDate.observe(this,forecastDataOb);
        weatherViewModel.aqiText.observe(this,aqiDataOb);
        weatherViewModel.suggestionDate.observe(this,suggestionOb);
        weatherViewModel.swipeRefresh.observe(this,swipeRefreshOb);
        weatherViewModel.bingPicSp.observe(this,bingPicOb);


        if(weatherString!=null){
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.getWeatherId();
            weatherViewModel.showWeatherInfo(weather);
        }else{
            weatherId = getIntent().getStringExtra("weather_id");
            weatherViewModel.requestWeather(weatherId);
        }

        if(bingImgString==null){
            weatherViewModel.loadBingPic();
        }else {
            Glide.with(this).load(bingImgString).into(bingImageView);
        }



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String weatherString = spf.getString("weather",null);
                Weather weather  = Utility.handleWeatherResponse(weatherString);
                final String weatherId = weather.basic.getWeatherId();
                weatherViewModel.requestWeather(weatherId);
            }
        });
        navChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
}