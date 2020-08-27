package com.three_fish.myweather.Gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    public String getStatus() {
        return status;
    }

    public Basic getBasic() {
        return basic;
    }

    public AQI getAqi() {
        return aqi;
    }

    public Now getNow() {
        return now;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public void setAqi(AQI aqi) {
        this.aqi = aqi;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }
}
