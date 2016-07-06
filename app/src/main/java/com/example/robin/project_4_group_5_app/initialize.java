package com.example.robin.project_4_group_5_app;

import android.graphics.Color;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jeroen on 01-07-16.
 */
public class initialize {

    public static void Graphs(ArrayList<ArrayList<ArrayList<Pair<String,String>>>> listQueries, BarChart graphContainers, LineChart graphStolenBikes, BarChart graphCombi, PieChart graphBrands, PieChart graphColors){

        ArrayList<ArrayList<Pair<String,String>>> listContainers = listQueries.get(0);
        ArrayList<ArrayList<Pair<String,String>>> listStolenBikes = listQueries.get(1);
        ArrayList<ArrayList<Pair<String,String>>> listCombiThefts = listQueries.get(2);
        ArrayList<ArrayList<Pair<String,String>>> listCombiContainers = listQueries.get(3);
        ArrayList<ArrayList<Pair<String,String>>> listBrands = listQueries.get(4);
        ArrayList<ArrayList<Pair<String,String>>> listColors = listQueries.get(5);

        graphContainers.setTouchEnabled(true);
        graphContainers.getLegend().setWordWrapEnabled(true);

        //      BAR-GRAPH       //

        ArrayList<String> labelsContainer = new ArrayList<>();
        for (ArrayList<Pair<String, String>> row:listContainers) {
            labelsContainer.add(row.get(1).second);
        }

        List<BarEntry> barEntryContainer = new ArrayList<>();
        for (int i = 0; i < listContainers.size(); i++){
                barEntryContainer.add(new BarEntry(Float.parseFloat(listContainers.get(i).get(0).second), i));
        }

        BarDataSet datasetContainer = new BarDataSet(barEntryContainer, "Aantal fietstrommels.");
        datasetContainer.setColors(ColorTemplate.JOYFUL_COLORS);
//        datasetContainer.setColor();
        //TODO:

        BarData dataContainer = new BarData(labelsContainer, datasetContainer);
        dataContainer.setValueTextSize(20);

        graphContainers.setData(dataContainer);
        graphContainers.setDescription("Plaatsen in Rotterdam met de meeste fietstrommels.");
        //TODO: in de gemeente van rotterdam
        graphContainers.resetViewPortOffsets();
        graphContainers.fitScreen();
        graphContainers.zoom(2.5f, 0f, 2, 0);

        graphContainers.animateY(3000);

        graphContainers.invalidate();

        //      LINE-GRAPH      //

        graphStolenBikes.setTouchEnabled(true);
        graphStolenBikes.getLegend().setWordWrapEnabled(true);

        ArrayList<String> xValsStolenBikes = new ArrayList<String>();
        ArrayList<Entry> entryStolenBikes = new ArrayList<Entry>();
        for (int i = 0; i < listStolenBikes.size(); i++){
            entryStolenBikes.add(new Entry(Float.parseFloat(listStolenBikes.get(i).get(0).second), i));
            xValsStolenBikes.add(listStolenBikes.get(i).get(1).second +  " " + listStolenBikes.get(i).get(2).second);
        }
        xValsStolenBikes.add("");

        LineDataSet lineDataSetStolenBikes = new LineDataSet(entryStolenBikes, "Aantal gestolen fietsen in Rotterdam.");
        lineDataSetStolenBikes.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetStolenBikes.setColor(Color.GRAY, 170);

        ArrayList<ILineDataSet> dataSetsStolenBikes = new ArrayList<ILineDataSet>();
        dataSetsStolenBikes.add(lineDataSetStolenBikes);

        LineData dataStolenBikes = new LineData(xValsStolenBikes, dataSetsStolenBikes);
        graphStolenBikes.setData(dataStolenBikes);

        graphStolenBikes.resetViewPortOffsets();
        graphStolenBikes.fitScreen();
        graphStolenBikes.zoom(9.5f, 0f, 2, 0);

        graphStolenBikes.invalidate();

        //      BAR-GRAPH       //

        graphCombi.setTouchEnabled(true);
        graphCombi.getLegend().setWordWrapEnabled(true);

        ArrayList<String> labelsCombi = new ArrayList<>();
        for (ArrayList<Pair<String, String>> row:listCombiThefts) {
            labelsCombi.add(row.get(1).second + " " + row.get(2).second);
        }

        List<BarEntry> barEntryCombiThefts = new ArrayList<>();
        List<BarEntry> barEntryCombiContainers = new ArrayList<>();
        for (int i = 0; i < listCombiThefts.size(); i++){
            barEntryCombiThefts.add(new BarEntry(Float.parseFloat(listCombiThefts.get(i).get(0).second), i));
            barEntryCombiContainers.add(new BarEntry(Float.parseFloat(listCombiContainers.get(0).get(0).second), i));
        }

        BarDataSet dataSetCombiThefts = new BarDataSet(barEntryCombiThefts, "Amount of bike thefts");
        dataSetCombiThefts.setColor(Color.RED, 170);
        BarDataSet dataSetCombiContainers = new BarDataSet(barEntryCombiContainers, "Amount of bike containers");
        dataSetCombiContainers.setColor(Color.BLUE, 70);

        ArrayList<IBarDataSet> dataSetsCombi = new ArrayList<>();
        dataSetsCombi.add(dataSetCombiThefts);
        dataSetsCombi.add(dataSetCombiContainers);

        BarData dataCombi = new BarData(labelsCombi, dataSetsCombi);

        graphCombi.setData(dataCombi);
//        graphCombi.setDescription("");
//        graphCombi.resetViewPortOffsets();
//        graphCombi.fitScreen();
//        graphCombi.zoom(3f, 0f, 2, 0);
//
//        graphCombi.animateY(3000);
//
//        graphCombi.invalidate();


        //      PIE-BRANDS      //

        graphBrands.setTouchEnabled(true);
        graphBrands.getLegend().setWordWrapEnabled(true);

        ArrayList<String> labelsBrands = new ArrayList<>();
        for (ArrayList<Pair<String, String>> row:listBrands) {
            labelsBrands.add(row.get(1).second);
        }

        List<Entry> entryBrands = new ArrayList<>();
        for (int i = 0; i < listBrands.size(); i++){
            entryBrands.add(new Entry(Float.parseFloat(listBrands.get(i).get(0).second), i));
        }

        PieDataSet dataSetBrands = new PieDataSet(entryBrands, "Populairste merk bij gestolen fietsen.");
        dataSetBrands.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData dataBrands = new PieData(labelsBrands, dataSetBrands);
        graphBrands.setData(dataBrands);

        graphBrands.setDescription("Brands Description.");

        graphBrands.invalidate();

        //      PIE-COLORS      //

        graphColors.setTouchEnabled(true);
        graphColors.getLegend().setWordWrapEnabled(true);

        ArrayList<String> labelsColors = new ArrayList<>();
        for (ArrayList<Pair<String, String>> row:listColors) {
            labelsColors.add(row.get(1).second);
        }

        List<Entry> entryColors = new ArrayList<>();
        for (int i = 0; i < listColors.size(); i++){
            entryColors.add(new Entry(Float.parseFloat(listColors.get(i).get(0).second), i));
        }

        PieDataSet dataSetColors = new PieDataSet(entryColors, "Populairste merk bij gestolen fietsen.");
        dataSetColors.setColors(ColorTemplate.LIBERTY_COLORS);
        //TODO: add custom colors in different class
        //NOTE: return int[] or only hex values?

        PieData dataColors = new PieData(labelsColors, dataSetColors);
        graphColors.setData(dataColors);

        graphColors.setDescription("Colors Description.");

        graphColors.invalidate();

    }
    public static void Combi(BarChart graphCombi, ArrayList<ArrayList<Pair<String,String>>> listCombiThefts, ArrayList<ArrayList<Pair<String,String>>> listCombiContainers, TextView textErrorMsg){

        List<String> stringList = graphCombi.getBarData().getXVals();

        List<BarEntry> barEntryCombiContainers = new ArrayList<>();;
        List<BarEntry> barEntryCombiThefts = new ArrayList<>();

        if (listCombiThefts.size()<30 && listCombiContainers.size() == 0){
            for (int i = 0; i < 30; i++) {
                barEntryCombiThefts.add(new BarEntry(0, i));
                //TODO: change TextView to say "No data for thefts."
                barEntryCombiContainers.add(new BarEntry(0, i));
                //TODO: change TextView to say "No data for containers."
                textErrorMsg.setText("No data found for thefts or containers in this area.");
            }
        }
        else if (listCombiThefts.size()<30){
            for (int i = 0; i < 30; i++) {
                barEntryCombiThefts.add(new BarEntry(0, i));
                //TODO: change TextView to say "No data for thefts."
                barEntryCombiContainers.add(new BarEntry(Float.parseFloat(listCombiContainers.get(0).get(0).second), i));
                textErrorMsg.setText("No data found for thefts in this area.");
            }
        }
        else if (listCombiContainers.size() == 0){
            for (int i = 0; i < 30; i++) {
                barEntryCombiThefts.add(new BarEntry(Float.parseFloat(listCombiThefts.get(i).get(0).second), i));
                barEntryCombiContainers.add(new BarEntry(0, i));
                //TODO: change TextView to say "No data for containers."
                textErrorMsg.setText("No data found for containers in this area.");
            }
        }
        else{
            for (int i = 0; i < 30; i++){
                barEntryCombiThefts.add(new BarEntry(Float.parseFloat(listCombiThefts.get(i).get(0).second), i));
                barEntryCombiContainers.add(new BarEntry(Float.parseFloat(listCombiContainers.get(0).get(0).second), i));
                textErrorMsg.setText("");
            }
        }

        BarDataSet dataSetCombiThefts = new BarDataSet(barEntryCombiThefts, "Amount of bike thefts");
        dataSetCombiThefts.setColor(Color.RED, 170);
        BarDataSet dataSetCombiContainers = new BarDataSet(barEntryCombiContainers, "Amount of bike containers");
        dataSetCombiContainers.setColor(Color.BLUE, 70);

        ArrayList<IBarDataSet> dataSetsCombi = new ArrayList<>();
        dataSetsCombi.add(dataSetCombiThefts);
        dataSetsCombi.add(dataSetCombiContainers);

        BarData dataCombi = new BarData(stringList, dataSetsCombi);

        graphCombi.setData(dataCombi);
        graphCombi.setDescription("");
        graphCombi.resetViewPortOffsets();
        graphCombi.fitScreen();
        graphCombi.zoom(8.7f, 0f, 2, 0);

        graphCombi.animateY(700);

        graphCombi.invalidate();
    }

    public static String getCompleteAddressString(double LATITUDE, double LONGITUDE, MainActivity mainActivity) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(mainActivity, Locale.getDefault());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current address", "Error(1): failed to load address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current address", "Error(2): failed to load address.");
        }
        return strAdd;
    }
}
