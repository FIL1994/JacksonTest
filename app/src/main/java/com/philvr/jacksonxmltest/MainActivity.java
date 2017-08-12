package com.philvr.jacksonxmltest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.philvr.jacksonxmltest.NYTimesBooks.BestSellers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private final String BASE_URL = "https://api.nytimes.com/svc/books/v3";
    private String API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API_KEY = getString(R.string.api_key);


        ObjectMapper mapper = new ObjectMapper();

        String url = "http://geocoder.ca/?locate=Thornton&geoit=xml&standard=1&topmatches=1&strictmode=1";

        new GetDataFromUrl(new GetDataFromUrl.TaskListener() {
            @Override
            public void onFinished(String result) {
                Log.d("result", result);
            }
        }).execute(url);

        getBestSellers();
    }

    private Map<String, String> toGenericObject(String jsonToParse){
        Map<String, String> properties = new HashMap<>();

        try{
            ObjectMapper mapper = new ObjectMapper();
            properties = mapper.readValue(jsonToParse, new TypeReference<Map<String, String>>() {
            });
        }
        catch(IOException e){
            Log.e("error", e.getMessage(), e);
        }
        return properties;
    }

    private List<Object> toObject(String jsonToParse){
        List<Object> properties = new ArrayList<Object>();

        try{
            ObjectMapper mapper = new ObjectMapper();
            properties = mapper.readValue(jsonToParse, new TypeReference<List<Object>>() {
            });
        }
        catch(IOException e){
            Log.e("error", e.getMessage(), e);
        }
        return properties;
    }

    private void getBestSellers(){
        String url = BASE_URL + "/lists/best-sellers/history.json"
                + "?api-key=" + API_KEY;

//        url = BASE_URL + "/reviews.json"
//                + "?api-key=" + API_KEY + "&author=george orwell";

        new GetDataFromUrl(new GetDataFromUrl.TaskListener() {
            @Override
            public void onFinished(String result) {
                Log.d("Best Sellers", result);
                System.out.println(result.length());

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    BestSellers bestSellers = objectMapper.readValue(result, BestSellers.class);
                }
                catch(Exception ex){
                    Log.d("Error", ex.getMessage(), ex);
                }

//                Gson gson = new Gson();
//                Type type = new TypeToken<Map<String, String>>(){}.getType();
//                Map<String, String> myMap = gson.fromJson(result, type);

//                Type type = new TypeToken<ArrayList<ArrayList<ArrayList<Map<String, Object>>>>>() {}.getType();
//                ArrayList<ArrayList<ArrayList<Map<String, Object>>>> data = gson.fromJson(result, type);

//                Map<String, String> bestSellers = toGenericObject(result);
//                printMap(bestSellers);

//                List<Object> bestSellers = toObject(result);
//                System.out.println("BEST:" + bestSellers.toString());

            }
        }).execute(url);

    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
