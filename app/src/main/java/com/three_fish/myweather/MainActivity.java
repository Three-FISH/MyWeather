package com.three_fish.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.three_fish.myweather.ui.main.AreaFragment;
import com.three_fish.myweather.ui.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AreaFragment.newInstance())
                    .commitNow();
        }
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
        if(spf.getString("weather",null)!=null){
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}