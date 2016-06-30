package com.example.robin.project_4_group_5_app;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public static query1 JsonToQuery1(String json) {
        try {
            JSONArray arr = new JSONArray(json);

            query1 q = new query1();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);

                System.out.println(o.toString());

                q.result.add(new Result(o.getString("amount"), o.getString("borough")));
            }

            return q;

        }
        catch(Exception e) {
            return new query1();
        }
    }

}

//      BACKUP      //

//public class JsonUtil {
//
//    public static query1 JsonToQuery1(String json) {
//        try {
//            JSONArray arr = new JSONArray(json);
//
//            query1 q = new query1();
//
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject o = arr.getJSONObject(i);
//
//                System.out.println(o.toString());
//
//                q.result.add(new Result(o.getString("amount"), o.getString("borough")));
//            }
//
//            return q;
//
//        }
//        catch(Exception e) {
//            return new query1();
//        }
//    }
//
//}