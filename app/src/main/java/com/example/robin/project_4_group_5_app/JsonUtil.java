package com.example.robin.project_4_group_5_app;

import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtil {

    public static ArrayList<ArrayList<Pair<String,String>>> extractJSON(String jsonString)
    {
        try {
            JSONArray jsonList = new JSONObject(jsonString).getJSONArray("result");

            ArrayList<ArrayList<Pair<String, String>>> data = new ArrayList<ArrayList<Pair<String, String>>>();

            for(int i = 0; i < jsonList.length(); i++) {
                ArrayList<Pair<String, String>> subData = new ArrayList<Pair<String, String>>();

                JSONObject jObject = jsonList.getJSONObject(i);
                Iterator<?> keys = jObject.keys();
                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    subData.add(new Pair(key, (String)jObject.get(key)));
                }
                data.add(subData);
            }
            return data;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}