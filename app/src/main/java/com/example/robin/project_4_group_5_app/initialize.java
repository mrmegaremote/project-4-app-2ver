package com.example.robin.project_4_group_5_app;

import android.util.Pair;
import android.widget.Spinner;

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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 01-07-16.
 */
public class initialize {

    public static void Graphs(ArrayList<ArrayList<ArrayList<Pair<String,String>>>> listQueries, BarChart graphContainers, LineChart graphStolenBikes, BarChart graphCombi, PieChart graphBrands, PieChart graphColors){

        ArrayList<ArrayList<Pair<String,String>>> listContainers = listQueries.get(0);
        ArrayList<ArrayList<Pair<String,String>>> listStolenBikes = listQueries.get(1);
        ArrayList<ArrayList<Pair<String,String>>> listCombi = listQueries.get(2);
        ArrayList<ArrayList<Pair<String,String>>> listBrands = listQueries.get(3);
        ArrayList<ArrayList<Pair<String,String>>> listColors = listQueries.get(4);


        //      BAR-GRAPH       //
        graphContainers.setTouchEnabled(true);

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

        BarData dataContainer = new BarData(labelsContainer, datasetContainer);

        graphContainers.setData(dataContainer);
        graphContainers.setDescription("5 gemeentes met de meeste fietstrommels.");

        graphContainers.animateY(3000);

        graphContainers.invalidate();

        //      LINE-GRAPH      //

        graphStolenBikes.setTouchEnabled(true);

        ArrayList<String> xValsStolenBikes = new ArrayList<String>();
        ArrayList<Entry> entryStolenBikes = new ArrayList<Entry>();
        for (int i = 0; i < listStolenBikes.size(); i++){
            entryStolenBikes.add(new Entry(Float.parseFloat(listStolenBikes.get(i).get(0).second), i));
            xValsStolenBikes.add(listStolenBikes.get(i).get(1).second +  " " + listStolenBikes.get(i).get(2).second);
        }
        xValsStolenBikes.add("");

        LineDataSet lineDataSetStolenBikes = new LineDataSet(entryStolenBikes, "Gestolen fietsen.");
        lineDataSetStolenBikes.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<ILineDataSet> dataSetsStolenBikes = new ArrayList<ILineDataSet>();
        dataSetsStolenBikes.add(lineDataSetStolenBikes);

        LineData dataStolenBikes = new LineData(xValsStolenBikes, dataSetsStolenBikes);
        dataStolenBikes.setValueTextSize(14f);

        graphStolenBikes.setData(dataStolenBikes);

        graphStolenBikes.invalidate();

        //      BAR-GRAPH       //

        graphCombi.setTouchEnabled(true);

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

        graphBrands.setDescription("Description.");

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

        graphColors.setDescription("Description.");

        graphColors.invalidate();

    }

    public static void Spinner(Spinner spinnerCombi){

    }
}
