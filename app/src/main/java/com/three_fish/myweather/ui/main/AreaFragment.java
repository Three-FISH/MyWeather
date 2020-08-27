package com.three_fish.myweather.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.three_fish.myweather.Database.City;
import com.three_fish.myweather.Database.County;
import com.three_fish.myweather.Database.Province;
import com.three_fish.myweather.MainActivity;
import com.three_fish.myweather.R;
import com.three_fish.myweather.ui.weather.WeatherActivity;
import com.three_fish.myweather.ui.weather.WeatherViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;

public class AreaFragment extends Fragment {

     public static final int LEVEL_PROVINCE = 1;
     public static final int LEVEL_CITY = 2;
     public static final int LEVEL_COUNTY = 3;
     private int currentLevel;

     AreaViewModel chooseViewModel;

     TextView titleText;
     ImageView backImg;
     ListView listView;
     ArrayAdapter<String> adapter;
     ArrayList<String> dataList = new ArrayList<>();
     ArrayList<County> countyDataList = new ArrayList<>();
     int currentCity;
     int currentCounty;
     String weatherId;
     ProgressBar progressBar;

    public AreaFragment() {
    }

    public static AreaFragment newInstance() {
        return new AreaFragment();
    }
    private Observer<ArrayList<String>> provinceOb=new Observer<ArrayList<String>>() {

        @Override
        public void onChanged(ArrayList<String> strings) {
            dataList = strings;
            adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
            listView.setAdapter(adapter);
            currentLevel = LEVEL_PROVINCE;
        }
    };
    private Observer<ArrayList<String>> cityOb = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> strings) {
            dataList = strings;
            adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
            listView.setAdapter(adapter);
            currentLevel = LEVEL_CITY;
        }
    };
    private Observer<ArrayList<String>> countyOb = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> strings) {
            dataList = strings;
            adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
            listView.setAdapter(adapter);
            currentLevel = LEVEL_COUNTY;
        }
    };
    private  Observer<ArrayList<County>> countyDataOb = new Observer<ArrayList<County>>() {
        @Override
        public void onChanged(ArrayList<County> counties) {
            countyDataList = counties;
        }
    };

    private Observer<String> titleTextOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            titleText.setText(s);
        }
    };
    private Observer<Boolean> progressOb = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if(aBoolean){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        }
    };
    private Observer<Boolean> backButtonOb = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if(aBoolean){
                backImg.setVisibility(View.VISIBLE);
            }else {
                 backImg.setVisibility(View.GONE);
            }
        }
    };
    private Observer<String> weatherIdOb = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            weatherId = s;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        titleText = view.findViewById(R.id.tv_title);
        backImg = view.findViewById(R.id.img_back);
        listView = view.findViewById(R.id.lv_chooseArea);
        progressBar = view.findViewById(R.id.pgb_loading);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chooseViewModel = ViewModelProviders.of(this).get(AreaViewModel.class);
        chooseViewModel.queryProvince();

        chooseViewModel.provinceList.observe(this,provinceOb);
        chooseViewModel.cityList.observe(this,cityOb);
        chooseViewModel.countyList.observe(this,countyOb);
        chooseViewModel.countyData.observe(this,countyDataOb);
        chooseViewModel.titleText.observe(this,titleTextOb);
        chooseViewModel.loading.observe(this,progressOb);
        chooseViewModel.backButton.observe(this,backButtonOb);
        chooseViewModel.weatherId.observe(this,weatherIdOb);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (currentLevel ==LEVEL_PROVINCE){
                    currentCity = position;
                    chooseViewModel.queryCity(position);
                }else if(currentLevel ==LEVEL_CITY){
                    currentCounty = position;
                    chooseViewModel.queryCounty(position);
                }else if(currentLevel ==LEVEL_COUNTY){
                    String weatherId = countyDataList.get(position).getWeatherId();;
                    if(getActivity() instanceof MainActivity){
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id",weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefreshLayout.setRefreshing(true);
                        activity.weatherViewModel.requestWeather(weatherId);
                    }

                }
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel ==LEVEL_COUNTY){
                    chooseViewModel.queryCity(currentCity);
                }else if(currentLevel == LEVEL_CITY){
                    chooseViewModel.queryProvince();
                }
            }
        });

    }

}