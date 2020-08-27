package com.three_fish.myweather.ui.main;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.three_fish.myweather.Database.City;
import com.three_fish.myweather.Database.County;
import com.three_fish.myweather.Database.Province;
import com.three_fish.myweather.Util.HttpUtil;
import com.three_fish.myweather.Util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AreaViewModel extends ViewModel {

    ArrayList<Province> mProvinceList;
    ArrayList<City> mCityList;
    ArrayList<County> mCountyList;
    ArrayList<String> dataList = new ArrayList<>();

    int cityPosition;
    int countyPosition;


    MutableLiveData<ArrayList<String>> _provinceList = new MutableLiveData<>();
    LiveData<ArrayList<String>> provinceList = _provinceList;

    MutableLiveData<ArrayList<String>> _cityList = new MutableLiveData<>();
    LiveData<ArrayList<String>> cityList = _cityList;

    MutableLiveData <ArrayList<String>> _countyList = new MutableLiveData<>();
    LiveData<ArrayList<String>> countyList = _countyList;

    MutableLiveData<ArrayList<County>> _countyData = new MutableLiveData<>();
    LiveData<ArrayList<County>> countyData = _countyData;

    MutableLiveData<String> _titleText = new MutableLiveData<>();
    LiveData<String> titleText = _titleText;

    MutableLiveData<Boolean> _backButton = new MutableLiveData<>();
    LiveData<Boolean> backButton = _backButton;

    MutableLiveData <Boolean> _loading = new MutableLiveData<>();
    LiveData<Boolean> loading = _loading;

    MutableLiveData <String> _weatherId = new MutableLiveData<>();
    LiveData<String> weatherId = _weatherId;

    void queryProvince(){
            _backButton.postValue(false);
            _titleText.postValue("中国");
            mProvinceList =(ArrayList<Province>) LitePal.findAll(Province.class);
            if(mProvinceList.size()>0){
                dataList.clear();
                for(Province province : mProvinceList){
                    dataList.add(province.getProvinceName());
                }
                _provinceList.postValue(dataList);
            }else {
                String address = "http://guolin.tech/api/china";
                queryFromServer(address,"province");
            }
    }
    void queryCity(int position){
        _loading.postValue(true);
        cityPosition = position;
        mCityList = (ArrayList<City>) LitePal.where("provinceId = ?",String.valueOf(mProvinceList.get(position).getId())).find(City.class);
        _titleText.postValue(mProvinceList.get(position).getProvinceName());
        if(mCityList.size()>0){
            dataList.clear();
            for(City city : mCityList){
                dataList.add(city.getCityName());
            }
            _cityList.postValue(dataList);
            _backButton.postValue(true);
            _loading.postValue(false);
        }else {
            String address = "http://guolin.tech/api/china/"+mProvinceList.get(position).getProvinceCode();
            queryFromServer(address,"city");
        }
    }

    void queryCounty(int position){
        _loading.postValue(true);
        countyPosition = position;
        mCountyList = (ArrayList<County>) LitePal.where("cityId = ?",String.valueOf((mCityList).get(position).getId())).find(County.class);
        _titleText.postValue(mCityList.get(position).getCityName());
        _countyData.postValue(mCountyList);
        if(mCountyList.size()>0){
            dataList.clear();
            for(County county :mCountyList){
                dataList.add(county.getCountyName());
            }
            _weatherId.postValue(mCountyList.get(position).getWeatherId());
            _countyList.postValue(dataList);
            _loading.postValue(false);
        }else {
            String address = "http://guolin.tech/api/china/"+mProvinceList.get(cityPosition).getProvinceCode()+"/"+mCityList.get(position).getCityCode();
            queryFromServer(address,"county");
        }
    }

    private void queryFromServer(String address, final String type) {
        HttpUtil.sendOkhttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if("province".equals(type)){
                   result = Utility.handleProvinceResponse(responseText);
                }else if("city".equals(type)){
                   result = Utility.handleCityResponse(responseText,mProvinceList.get(cityPosition).getId());
                }else if("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,mCityList.get(countyPosition).getId());
                }

                if(result){
                    if("province".equals(type)){
                        queryProvince();
                    }else if("city".equals(type)){
                        queryCity(cityPosition);
                    }else if("county".equals(type)){
                        queryCounty(countyPosition);
                    }
                }
            }
        });


    }




}