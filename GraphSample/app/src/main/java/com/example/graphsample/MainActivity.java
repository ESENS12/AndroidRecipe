package com.example.graphsample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PieChart pieChart = (PieChart)findViewById(R.id.chart);
//        List<Entry> entries = new ArrayList<Entry>();
//        entries.add(new Entry());
//        LineDataSet dataSet = new LineDataSet(entries, "label");
//        dataSet.setColor(getResources().getColor(R.color.colorPrimaryDark));

        PieChart pieChart = findViewById(R.id.chart);

//        ArrayList NoOfEmp = new ArrayList();
//
//        NoOfEmp.add(new Entry(945f, 0));
//        NoOfEmp.add(new Entry(1040f, 1));
//        NoOfEmp.add(new Entry(1133f, 2));
//        NoOfEmp.add(new Entry(1240f, 3));
//        NoOfEmp.add(new Entry(1369f, 4));
//        NoOfEmp.add(new Entry(1487f, 5));
//        NoOfEmp.add(new Entry(1501f, 6));
//        NoOfEmp.add(new Entry(1645f, 7));
//        NoOfEmp.add(new Entry(1578f, 8));
//        NoOfEmp.add(new Entry(1695f, 9));
//
//
//        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
//
//        ArrayList year = new ArrayList();
//
//        year.add("2008");
//        year.add("2009");
//        year.add("2010");
//        year.add("2011");
//        year.add("2012");
//        year.add("2013");
//        year.add("2014");
//        year.add("2015");
//        year.add("2016");
//        year.add("2017");
//        //PieData data = new PieData(year, dataSet);
//        //pieChart.setData(data);
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        pieChart.animateXY(5000, 5000);

        ArrayList<PieEntry> entries = new ArrayList<>();

        //for (int i = 0; i < 10 ; i++) {
            //entries.add(new PieEntry((float) ((Math.random() * 3) + 5 / 5)));
            entries.add(new PieEntry(100));

        //}

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter(pieChart));
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
    }


}
