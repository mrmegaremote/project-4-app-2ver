package com.example.robin.project_4_group_5_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDebug;
    private String jsonString;

    private static final String JSON_URL = "http://188.166.26.149/userstory1.php?querynum=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void initializeTabs()
    {
        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();

        TabHost.TabSpec tabSpec = tabhost.newTabSpec("StolenBikes");
        tabSpec.setContent(R.id.tabStolenBikes);
        tabSpec.setIndicator("Stolen Bikes");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("BikeContainers");
        tabSpec.setContent(R.id.tabContainers);
        tabSpec.setIndicator("Bike Cont.");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("Combi");
        tabSpec.setContent(R.id.tabCombi);
        tabSpec.setIndicator("Combi- Graph");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("PieBrands");
        tabSpec.setContent(R.id.tabBrands);
        tabSpec.setIndicator("Pie Brands");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("PieColors");
        tabSpec.setContent(R.id.tabColors);
        tabSpec.setIndicator("Pie Colors");
        tabhost.addTab(tabSpec);
    }

    public void HomeButtonClick(View view)
    {
        setContentView(R.layout.screen_1);
    }

    public void secondHomeButtonClick(View view)
    {
        setContentView(R.layout.tabbedgraph);

        BarChart graphContainers = (BarChart) findViewById(R.id.graphContainers);
        LineChart graphStolenBikes = (LineChart) findViewById(R.id.graphStolenBikes);
        BarChart graphCombi = (BarChart) findViewById(R.id.graphCombi);
        PieChart graphBrands = (PieChart) findViewById(R.id.graphBrands);
        PieChart graphColors = (PieChart) findViewById(R.id.graphColors);

        initializeTabs();
        ArrayList<ArrayList<ArrayList<Pair<String,String>>>> listQueries = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            listQueries.add(initializeJSON(Integer.toString(i+1)));
        }
            listQueries.add(initializeJSON("6"));
            listQueries.add(initializeJSON("7"));

        initialize.Graphs(listQueries, graphContainers, graphStolenBikes, graphCombi, graphBrands, graphColors);

        Spinner spinnerCombi = (Spinner) findViewById(R.id.spinnerCombi);

        initialize.Spinner(spinnerCombi);

//        textViewDebug = (TextView) findViewById(R.id.textViewDebug);
    }
    private ArrayList<ArrayList<Pair<String,String>>> initializeJSON(String userstoryQueryNumber) {
        this.jsonString = getJSON(JSON_URL, userstoryQueryNumber);
        return JsonUtil.extractJSON(this.jsonString);
    }

    private String getJSON(final String url, String userstoryQueryNumber) {
        class GetJSON extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;
            String jsonString = "Test!";

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Wan moment...", null, true, true);

            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;

                try{
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                } catch(Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    return e.toString();
                }

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
            }
        }

        GetJSON gj = new GetJSON();
        String newurl = url.concat(userstoryQueryNumber);
        gj.execute(newurl);
        try {
            gj.jsonString = (gj.get());
//            textViewDebug.setText(gj.jsonString);

        }
        catch (Exception e)
        {
            System.exit(1337);
        }
        return gj.jsonString;
    }

    public void returnButtonTabbed(View view)
    {
        setContentView(R.layout.activity_main);
    }

    public void OnBackButton(View view)
    {
        setContentView(R.layout.content_main);
    }

    public void OnButtonClick(View view)
    {
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graph.addSeries(series);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
