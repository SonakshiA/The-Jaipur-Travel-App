package com.example.jaipurweather;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch (MalformedURLException e){
            Log.e("Malformed url",e.getMessage());
        }
        return url;
    }

    private static String makeHttpsRequest(URL url) throws IOException{
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e("IOException",e.getMessage());
        }finally{
            if (urlConnection!=null){
                urlConnection.disconnect();
            } if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


    private static List<Weather> extractFromJSON(String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        } else {
            List<Weather> weatherComponents = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject jsonObject2 = jsonObject.getJSONObject("current");
                Double temp = jsonObject2.optDouble("temp_c");
                Double feelsLike = jsonObject2.optDouble("feelslike_c");
                Double windSpeed = jsonObject2.optDouble("wind_kph");
                Double precipitation = jsonObject2.optDouble("precip_mm");
                Double humidity = jsonObject2.optDouble("humidity");
                JSONObject condition = jsonObject2.optJSONObject("condition");
                String cond = condition.optString("text");
                weatherComponents.add(new Weather(temp, feelsLike, windSpeed, humidity, cond, precipitation));

            } catch (JSONException e) {
                Log.e("JSONException",e.getMessage());
            }
            return weatherComponents;
        }
    }

    public static List<Weather> fetchWeatherData(String requestURL){
        URL url = createURL(requestURL);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpsRequest(url);
        } catch (IOException e){
            Log.e("IOException",e.getMessage());
        }
        List<Weather> weatherList = extractFromJSON(jsonResponse);
        return weatherList;
    }
}


/*
createUrl -> (HTTP Request -> read from stream) -> extract features

couple this into fetchJSON
 */