package com.example.robin.project_4_group_5_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_URL = "http://188.166.26.149/userstory1.php?querynum=";
    public FileOutputStream fOut;
    TextView textViewReader;
    FileInputStream fin;
    private TextView textViewDebug;
    private String jsonString;
    private BarChart graphContainers;
    private LineChart graphStolenBikes;
    private BarChart graphCombi;
    private PieChart graphBrands;
    private PieChart graphColors;
    private TextView textErrorMsg;
    private Typeface ComicSansMS;


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

        ComicSansMS = Typeface.createFromAsset(getAssets(), "comicsansms.ttf");

    }

    private void initializeTabs() {
        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);

        tabhost.setup();
        TabHost.TabSpec tabSpec = tabhost.newTabSpec("StolenBikes");
        tabSpec.setContent(R.id.tabStolenBikes);
        tabSpec.setIndicator("Theft");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("BikeContainers");
        tabSpec.setContent(R.id.tabContainers);
        tabSpec.setIndicator("Bike Containers");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("Combi");
        tabSpec.setContent(R.id.tabCombi);
        tabSpec.setIndicator("Combi Graph");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("PieBrands");
        tabSpec.setContent(R.id.tabBrands);
        tabSpec.setIndicator("Brand");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("PieColors");
        tabSpec.setContent(R.id.tabColors);
        tabSpec.setIndicator("Color");
        tabhost.addTab(tabSpec);
    }

    public void HomeButtonClick(View view) throws FileNotFoundException {
        textViewReader = (TextView) findViewById(R.id.textViewReader);

        //      MAPS        //
//        setContentView(R.layout.activity_maps);
        ArrayList<ArrayList<Pair<String, String>>> lnglatContainers = initializeJSON("9");

        ArrayList<String> latContainers = new ArrayList<>();
        ArrayList<String> lngContainers = new ArrayList<>();

        for (ArrayList<Pair<String, String>> row:lnglatContainers) {
            latContainers.add(row.get(0).second);
            lngContainers.add(row.get(1).second);
        }

        Bundle extrasContainers = new Bundle();
        extrasContainers.putStringArrayList("0", latContainers);
        extrasContainers.putStringArrayList("1", lngContainers);

        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtras(extrasContainers);

        startActivity(intent);
    }

    public void OnSaveClick(View view) throws IOException {
        fOut = openFileOutput("file.txt", MODE_PRIVATE);
        String str = "this is where the location will be saved";
        fOut.write(str.getBytes());
        fOut.close();
        Toast t = Toast.makeText(MainActivity.this, "the location has been saved", Toast.LENGTH_SHORT);
        t.show();
    }

    public void OnReadClick(View view) throws IOException {
        try {
            fin = openFileInput("file.txt");
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }
            if (temp.length() > 0) {
                textViewReader.setText(temp);
                Toast t = Toast.makeText(MainActivity.this, "A location has been found", Toast.LENGTH_SHORT);
                t.show();
            }
        } catch (Exception e) {return;
        }

        //string temp contains all the data of the file.
        fin.close();
    }

    public void OnDeleteClick(View view) throws IOException {
        fOut = openFileOutput("file.txt", MODE_PRIVATE);
        String str = "";
        fOut.write(str.getBytes());
        fOut.close();
        Toast t = Toast.makeText(MainActivity.this, "The saved location has been deleted.", Toast.LENGTH_SHORT);
        t.show();
    }

    public void secondHomeButtonClick(View view)
    {
        setContentView(R.layout.tabbedgraph);

        graphContainers = (BarChart) findViewById(R.id.graphContainers);
        graphStolenBikes = (LineChart) findViewById(R.id.graphStolenBikes);
        graphCombi = (BarChart) findViewById(R.id.graphCombi);
        graphBrands = (PieChart) findViewById(R.id.graphBrands);
        graphColors = (PieChart) findViewById(R.id.graphColors);
        textErrorMsg = (TextView) findViewById(R.id.textErrorMsg);
        textErrorMsg.setTypeface(ComicSansMS);

        initializeTabs();

        ArrayList<ArrayList<ArrayList<Pair<String,String>>>> listQueries = new ArrayList<>();
        listQueries.add(initializeJSON("1"));
        listQueries.add(initializeJSON("2"));
        listQueries.add(initializeJSON("3&val='Overschie'"));
        listQueries.add(initializeJSON("4&val='Overschie'"));
        listQueries.add(initializeJSON("6"));
        listQueries.add(initializeJSON("7"));

        initialize.Graphs(listQueries, graphContainers, graphStolenBikes, graphCombi, graphBrands, graphColors);

        Spinner spinnerCombi = (Spinner) findViewById(R.id.spinnerCombi);

        initializeSpinner(spinnerCombi, graphCombi, this);

//        textViewDebug = (TextView) findViewById(R.id.textViewDebug);
    }

    private ArrayList<ArrayList<Pair<String,String>>> initializeJSON(String userstoryQueryNumber) {
        this.jsonString = getJSON(JSON_URL, userstoryQueryNumber);
        return JsonUtil.extractJSON(this.jsonString);
    }

    private String getJSON(final String url, String userstoryQueryNumber) {
        class GetJSON extends AsyncTask<String, Void, String>
        {
            String jsonString = "Test!";

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
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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

    private void initializeSpinner(Spinner spinner, BarChart graphCombi, final MainActivity mainActivity){
        ArrayList<String> spinnerArray = new ArrayList<>();
        ArrayList<ArrayList<Pair<String,String>>> listSpinner = initializeJSON("5");

        for (ArrayList<Pair<String, String>> row:listSpinner) {
            spinnerArray.add(row.get(1).second);
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView valView = (TextView) view;
                CharSequence val = valView.getText();

                initialize.Combi(mainActivity.graphCombi, mainActivity.initializeJSON("3&val='"+val+"'"), mainActivity.initializeJSON("4&val='"+val+"'"), mainActivity.textErrorMsg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void returnButtonTabbed(View view)
    {
        setContentView(R.layout.activity_main);
    }

    public void OnBackButton(View view) {
        setContentView(R.layout.activity_main);
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
