package com.example.jaipurweather;

import android.content.AsyncTaskLoader;
import android.app.LoaderManager;
import android.content.Context;

import java.util.List;

public class WeatherLoader extends AsyncTaskLoader<List<Weather>> {
    private String url;
    public WeatherLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Weather> loadInBackground() {
        if (this.url==null){
            return null;
        }else{
            List<Weather> weatherComponents = QueryUtils.fetchWeatherData(url);
            return weatherComponents;
        }
    }
}
