package com.example.robin.project_4_group_5_app;

import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONAdapter {


    public static ArrayList<ArrayList<Pair<String,String>>> initializeJSON(String userstoryQueryNumber) {
        final String JSON_URL = "http://188.166.26.149/userstory1.php?querynum=";

        String jsonString = getJSON(JSON_URL, userstoryQueryNumber);
        return JSONAdapter.extractJSON(jsonString);
    }

    private static String getJSON(final String url, String userstoryQueryNumber) {
        class GetJSON extends AsyncTask<String, Void, String>
        {
            String jsonString = "Nothing.";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;

                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return e.toString();
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }

        GetJSON gj = new GetJSON();
        String newurl = url.concat(userstoryQueryNumber);
        gj.execute(newurl);
        try {
            gj.jsonString = (gj.get());
//            textViewDebug.setText(gj.jsonString);

        } catch (Exception e) {
            System.exit(1337);
        }
        return gj.jsonString;
    }

    private static ArrayList<ArrayList<Pair<String,String>>> extractJSON(String jsonString)
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