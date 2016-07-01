package com.example.robin.project_4_group_5_app;

import android.util.Pair;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 01-07-16.
 */
public class initialize {

    public static void Graphs(BarChart graphContainers, LineChart graphStolenBikes, BarChart graphCombi, ArrayList<ArrayList<ArrayList<Pair<String,String>>>> listQueries){

        ArrayList<ArrayList<Pair<String,String>>> list0 = listQueries.get(0);
        ArrayList<ArrayList<Pair<String,String>>> list1 = listQueries.get(1);
        ArrayList<ArrayList<Pair<String,String>>> list2 = listQueries.get(2);

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
    }
}
