package com.example.jaipurweather;

public class Weather {
    private double temp;
    private double feelsLike;
    private double windSpeed;
    private double humidity;
    private String condition;
    private double precipitation;

    public Weather(double temp, double feelsLike, double windSpeed, double humidity, String condition, double precipitation){
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.condition = condition;
        this.precipitation = precipitation;
    }

    public double getTemperature(){
        return this.temp;
    }

    public double getFeelsLike(){
        return this.feelsLike;
    }

    public double getWindSpeed(){
        return this.windSpeed;
    }

    public double getHumidity(){
        return this.humidity;
    }

    public String getCondition(){
        return this.condition;
    }

    public double getPrecipitation(){
        return this.precipitation;
    }
}
