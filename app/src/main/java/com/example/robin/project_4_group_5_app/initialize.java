package com.example.robin.project_4_group_5_app;

import android.graphics.Color;
import android.util.Pair;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.Spinner;
=======
>>>>>>> parent of 85a9f30... Added two pie charts, made pieBrands work, formatting changes

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
<<<<<<< HEAD
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
=======
>>>>>>> parent of 85a9f30... Added two pie charts, made pieBrands work, formatting changes
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 01-07-16.
 */
public class initialize {

    public static void Graphs(BarChart graphContainers, LineChart graphStolenBikes, BarChart graphCombi, ArrayList<ArrayList<ArrayList<Pair<String,String>>>> listQueries){

<<<<<<< HEAD
        ArrayList<ArrayList<Pair<String,String>>> listContainers = listQueries.get(0);
        ArrayList<ArrayList<Pair<String,String>>> listStolenBikes = listQueries.get(1);
        ArrayList<ArrayList<Pair<String,String>>> listCombiThefts = listQueries.get(2);
        ArrayList<ArrayList<Pair<String,String>>> listCombiContainers = listQueries.get(3);
        ArrayList<ArrayList<Pair<String,String>>> listBrands = listQueries.get(4);
        ArrayList<ArrayList<Pair<String,String>>> listColors = listQueries.get(5);
=======
        ArrayList<ArrayList<Pair<String,String>>> list0 = listQueries.get(0);
        ArrayList<ArrayList<Pair<String,String>>> list1 = listQueries.get(1);
        ArrayList<ArrayList<Pair<String,String>>> list2 = listQueries.get(2);
>>>>>>> parent of 85a9f30... Added two pie charts, made pieBrands work, formatting changes

        graphContainers.setTouchEnabled(true);
        graphCombi.setTouchEnabled(true);

        //      BAR-GRAPH       //
        ArrayList<String> labelsContainer = new ArrayList<>();

        for (ArrayList<Pair<String, String>> row:list0) {
            labelsContainer.add(row.get(1).second);
        }

        List<BarEntry> barEntryContainer = new ArrayList<>();
        for (int i = 0; i < list0.size(); i++){
                barEntryContainer.add(new BarEntry(Float.parseFloat(list0.get(i).get(0).second), i));
        }

        BarDataSet datasetContainer = new BarDataSet(barEntryContainer, "Aantal fietstrommels.");
        datasetContainer.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData dataContainer = new BarData(labelsContainer, datasetContainer);

        graphContainers.setData(dataContainer);
        graphContainers.setDescription("5 gemeentes met de meeste fietstrommels.");

        graphContainers.animateY(3000);

        graphContainers.invalidate();

        //      LINE-GRAPH      //

        graphStolenBikes.setTouchEnabled(true);

        ArrayList<String> xValsStolenBikes = new ArrayList<String>();
        ArrayList<Entry> entryStolenBikes = new ArrayList<Entry>();
        for (int i = 0; i < list1.size(); i++){
            entryStolenBikes.add(new Entry(Float.parseFloat(list1.get(i).get(0).second), i));
            xValsStolenBikes.add(list1.get(i).get(1).second +  " " + list1.get(i).get(2).second);
        }
        xValsStolenBikes.add("");

        LineDataSet lineDataSetStolenBikes = new LineDataSet(entryStolenBikes, "Gestolen fietsen.");
        lineDataSetStolenBikes.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<ILineDataSet> dataSetsStolenBikes = new ArrayList<ILineDataSet>();
        dataSetsStolenBikes.add(lineDataSetStolenBikes);


        LineData dataStolenBikes = new LineData(xValsStolenBikes, dataSetsStolenBikes);
        graphStolenBikes.setData(dataStolenBikes);

        graphStolenBikes.invalidate();
<<<<<<< HEAD

        //      BAR-GRAPH       //

        graphCombi.setTouchEnabled(true);

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
        graphCombi.setDescription("");
        graphCombi.zoom(8.8f, 1f, 2, 1);

        graphCombi.animateY(3000);

        graphCombi.invalidate();


        //      PIE-BRANDS      //

        graphBrands.setTouchEnabled(true);

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

=======
>>>>>>> parent of 85a9f30... Added two pie charts, made pieBrands work, formatting changes
    }
}
