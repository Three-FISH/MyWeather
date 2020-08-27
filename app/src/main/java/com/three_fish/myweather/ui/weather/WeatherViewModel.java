package com.three_fish.myweather.ui.weather;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.three_fish.myweather.Gson.Forecast;
import com.three_fish.myweather.Gson.Weather;
import com.three_fish.myweather.MainActivity;
import com.three_fish.myweather.Util.HttpUtil;
import com.three_fish.myweather.Util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.model.Table_Schema;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {
    MutableLiveData<String> _weatherSp = new MutableLiveData<>();
    LiveData<String> weatherSp = _weatherSp;
    MutableLiveData<String> _bingPicSp = new MutableLiveData<>();
    LiveData<String> bingPicSp = _bingPicSp;
    MutableLiveData<String> _toastText = new MutableLiveData<>();
    LiveData<String> toastText = _toastText;
    MutableLiveData<Boolean> _swipeRefresh = new MutableLiveData<>();
    LiveData<Boolean> swipeRefresh = _swipeRefresh;

    MutableLiveData <String> _cityTitle = new MutableLiveData<>();
    LiveData<String> cityTitle = _cityTitle;
    MutableLiveData <String> _updateTime = new MutableLiveData<>();
    LiveData<String> updateTime = _updateTime;
    MutableLiveData <String> _degreeText = new MutableLiveData<>();
    LiveData<String> degreeText = _degreeText;
    MutableLiveData<String> _weatherInfoText = new MutableLiveData<>();
    LiveData<String> weatherInfoText = _weatherInfoText;
    MutableLiveData<ArrayList<Forecast>> _forecastDate = new MutableLiveData<>();
    LiveData<ArrayList<Forecast>> forecastDate = _forecastDate;
    MutableLiveData<ArrayList<String>> _aqiText =new MutableLiveData<>();
    LiveData<ArrayList<String>> aqiText = _aqiText;
    MutableLiveData<ArrayList<String>> _suggestionData = new MutableLiveData<>();
    LiveData<ArrayList<String>> suggestionDate = _suggestionData;





    public void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather/?cityid="+weatherId + "&key=7e1bb733067e41fea7c3de1e6204b11d";
        HttpUtil.sendOkhttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                if(weather!=null&&"ok".equals(weather.status)){
                    _weatherSp.postValue(responseText);
                    showWeatherInfo(weather);
                }else {
                    _toastText.postValue("加载失败了......");
                }
                _swipeRefresh.postValue(false);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                _toastText.postValue("加载失败了......");
                _swipeRefresh.postValue(false);
            }
        });
    }


    public void showWeatherInfo(Weather weather) {
            _cityTitle.postValue(weather.basic.getCityName());
            _updateTime.postValue(weather.basic.getUpdate().getUpdateTime().split(" ")[1]);
            _degreeText.postValue(weather.now.getTmperature()+"℃");
            _weatherInfoText.postValue(weather.now.getMore().getInfo());
            ArrayList<Forecast> forecasts = new ArrayList<>();
            for(Forecast forecast :weather.forecastList){
                Forecast forecastItem = new Forecast();
                forecastItem.setDate(forecast.getDate());
                forecastItem.setMore(forecast.getMore());
                forecastItem.setTmperature(forecast.getTmperature());
                forecasts.add(forecastItem);
            }
            _forecastDate.postValue(forecasts);
            if(weather.aqi!=null){
                ArrayList<String> aqiData = new ArrayList<>();
                aqiData.add(weather.aqi.getCity().getAqi());
                aqiData.add(weather.aqi.getCity().getPm25());
                _aqiText.postValue(aqiData);
            }
            ArrayList<String> suggestionData = new ArrayList<>();
            suggestionData.add("舒适度-->"+weather.suggestion.getComfort().getInfo());
            suggestionData.add("运动指数-->"+weather.suggestion.getSport().getInfo());
            suggestionData.add("洗车指数-->"+weather.suggestion.getCarWash().getInfo());
            _suggestionData.postValue(suggestionData);

    }
    public void loadBingPic() {
        final String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkhttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String bingPic = response.body().string();
                _bingPicSp.postValue(bingPic);
            }
        });
    }
}

