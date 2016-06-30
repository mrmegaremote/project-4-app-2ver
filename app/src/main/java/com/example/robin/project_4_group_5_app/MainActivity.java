package com.example.robin.project_4_group_5_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

//    private int userstoryQueryNumber;

    public static final String MY_JSON ="MY_JSON";

    private static final String JSON_URL = "http://188.166.26.149/userstory1.php?querynum=";

    JSONArray userstory2 = null;

    ArrayList<HashMap<String, String>> theftList;

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theftList = new ArrayList<HashMap<String, String>>();

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
        tabSpec.setIndicator("Bike Containers");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("Combi");
        tabSpec.setContent(R.id.tabCombi);
        tabSpec.setIndicator("Combination");
        tabhost.addTab(tabSpec);
    }

    public void HomeButtonClick(View view)
    {
        setContentView(R.layout.screen_1);
    }

    public void secondHomeButtonClick(View view)
    {
        initializeGraphs("1");
        initializeTabs();
    }

    private void initializeJSON(String userstoryQueryNumber) {
        getJSON(JSON_URL ,userstoryQueryNumber);
    }

    private void getJSON(String url, String userstoryQueryNumber) {
        class GetJSON extends AsyncTask<String, Void, String>{
            ProgressDialog loading;

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
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ParseJSON.class);
                intent.putExtra(MY_JSON,s);
                startActivity(intent);
            }
        }
        GetJSON gj = new GetJSON();
        String newurl = url.concat(userstoryQueryNumber);
        gj.execute(newurl);
    }

    private void initializeGraphs(String userstoryQueryNumber)
    {
        initializeJSON(userstoryQueryNumber);


        LineChart graphStolenBikes = (LineChart) findViewById(R.id.graphStolenBikes);
        BarChart graphContainers = (BarChart) findViewById(R.id.graphContainers);
        BarChart graphCombi = (BarChart) findViewById(R.id.graphCombi);

        graphStolenBikes.setTouchEnabled(true);
        graphContainers.setTouchEnabled(true);
        graphCombi.setTouchEnabled(true);

        //ArrayList<Entry>          Lijst met Entries/punten die later gekoppeld worden tot een line.

        ArrayList<Entry> dataStolenBikes1 = new ArrayList<Entry>();
        ArrayList<Entry> dataStolenBikes2 = new ArrayList<Entry>();

        //Entry                     1 punt binnen een ArrayList<Entry> / line.
        //Entry(int WaardeOpY-axis, int WaarOpX-axis)

        Entry entryStolenBikes1 = new Entry(12, 0);
        dataStolenBikes1.add(entryStolenBikes1);
        Entry entryStolenBikes2 = new Entry(14, 1);
        dataStolenBikes1.add(entryStolenBikes2);

        Entry entryStolenBikes3 = new Entry(15, 0);
        dataStolenBikes2.add(entryStolenBikes3);
        Entry entryStolenBikes4 = new Entry(10, 1);
        dataStolenBikes2.add(entryStolenBikes4);

        //LineDataSet               Verbindt de alle punten binnen de ArrayList<Entry> aan elkaar
        //                          en geeft de line een naam en kleur in de legenda.

        LineDataSet lineDataSetStolenBikes1 = new LineDataSet(dataStolenBikes1, "Company 1");
        lineDataSetStolenBikes1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet lineDataSetStolenBikes2 = new LineDataSet(dataStolenBikes2, "Company 2");
        lineDataSetStolenBikes1.setAxisDependency(YAxis.AxisDependency.LEFT);

        //ArrayList<ILineDataSet>   Verzamelt alle lines bij elkaar als 1 line dataset.

        ArrayList<ILineDataSet> dataSetsStolenBikes = new ArrayList<ILineDataSet>();
        dataSetsStolenBikes.add(lineDataSetStolenBikes1);
        dataSetsStolenBikes.add(lineDataSetStolenBikes2);

        //ArrayList<String> xVals   ArrayList met de naam van elke lijn van de X-axis op de grid/graph.

        ArrayList<String> xValsStolenBikes = new ArrayList<String>();
        xValsStolenBikes.add("1.Q"); xValsStolenBikes.add("2.Q"); xValsStolenBikes.add("3.Q"); xValsStolenBikes.add("4.Q");

        //LineData                  Combinatie van LineDataSet en de X-axis specificaties/eigenschappen.

        LineData dataStolenBikes = new LineData(xValsStolenBikes, dataSetsStolenBikes);

        //XXX.setData(LineData)     Geeft de LineData aan de graph.

        graphStolenBikes.setData(dataStolenBikes);

        //XXX.invalidate()          Updatet de graph om alle wijzingen van gegevens (van hiervoor) door te voeren.

        graphStolenBikes.invalidate();

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
