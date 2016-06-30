package com.example.robin.project_4_group_5_app;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jeroen on 30-06-16.
 */
public class ParseJSON extends ActionBarActivity implements View.OnClickListener
{

    private String myJSONString;

    private static final String JSON_ARRAY ="result";

    private static final String TAG_AMOUNT="amount";
    private static final String TAG_BOROUGH="borough";

    private JSONArray jsonList = null;

    private int TRACK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbedgraph);

        Intent intent = getIntent();
        myJSONString = intent.getStringExtra(MainActivity.MY_JSON);

        extractJSON();

        showData();
    }

    private void extractJSON()
    {
        try {
            JSONObject jsonObject = new JSONObject(myJSONString);
            jsonList = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showData() {
        Toast.makeText(ParseJSON.this, jsonList.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

    }
}
