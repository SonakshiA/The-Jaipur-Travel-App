package com.example.jaipurweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.LoaderManager;
import android.content.Context;

import android.content.Loader;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Weather>> {
    Weather weatherComponents;
    private static final String REQUEST_URL = "https://api.weatherapi.com/v1/current.json?key=dce01c1205384d9289482401220105&q=Jaipur";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView temperature = (TextView) findViewById(R.id.temperature);
//        temperature.setText("42");

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    private String tempFormatter(Double temp){
        DecimalFormat temperatureFormat = new DecimalFormat("0");
        return temperatureFormat.format(temp);
    }

    private int getTemperatureColor(Double temp){
        int colourResourceID;
        if (temp>43){
            colourResourceID = R.color.red;
        } else if (temp>=36 && temp<=43){
            colourResourceID = R.color.orange;
        } else if (temp>=30 && temp<=35){
            colourResourceID = R.color.yellow;
        } else if (temp>=20 && temp<30){
            colourResourceID= R.color.blue;
        } else{
            colourResourceID = R.color.light_blue;
        }
        return ContextCompat.getColor(this,colourResourceID);
    }



    @NonNull
    @Override
    public Loader<List<Weather>> onCreateLoader(int id, @Nullable Bundle args) {
        return new WeatherLoader(this,REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Weather>> loader, List<Weather> data) {

        TextView condition = (TextView) findViewById(R.id.condition);
        condition.setText(data.get(0).getCondition());

        TextView temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(tempFormatter(data.get(0).getTemperature()));

        TextView windSpeed = (TextView) findViewById(R.id.wind_speed);
        windSpeed.setText(String.valueOf(data.get(0).getWindSpeed()));

        TextView feelsLike = (TextView) findViewById(R.id.feels_like);
        feelsLike.setText(tempFormatter(data.get(0).getFeelsLike()));

        TextView humidity = (TextView) findViewById(R.id.humidity);
        humidity.setText(String.valueOf(data.get(0).getHumidity()));

        TextView precipitation = (TextView) findViewById(R.id.precipitation);
        precipitation.setText(String.valueOf(data.get(0).getPrecipitation()));

        GradientDrawable temperatureCircle = (GradientDrawable) temperature.getBackground();
        int tempColor = getTemperatureColor(data.get(0).getTemperature());
        temperatureCircle.setColor(tempColor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Weather>> loader) {

    }
}