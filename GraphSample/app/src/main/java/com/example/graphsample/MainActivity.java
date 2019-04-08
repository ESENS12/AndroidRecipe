package com.example.graphsample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PieChart pieChart = findViewById(R.id.chart);

        //pieChart.set
        //description
        pieChart.getDescription().setEnabled(false);
        //description 컬러 안내(?)
        pieChart.getLegend().setEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();

        //for (int i = 0; i < 10 ; i++) {
            //entries.add(new PieEntry((float) ((Math.random() * 3) + 5 / 5)));
        entries.add(new PieEntry(30));
        entries.add(new PieEntry(70));

        //}

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        //데이터간 구분 간격
        dataSet.setSliceSpace(3f);

        //graph 내부 텍스트 크기 및 색상 조정
        dataSet.setValueTextSize(30);
        dataSet.setValueTextColor(getResources().getColor(R.color.white));

        dataSet.setColors(new int[] { R.color.white, R.color.colorAccent}, this);

        dataSet.setDrawIcons(false);
        dataSet.setIconsOffset(new MPPointF(0, 40));

        //차트 크기
        dataSet.setSelectionShift(10f);

        //int colors [] = new int[2];
//
//        colors[0] = getResources().getColor(R.color.colorPrimaryDark);
//        colors[1] = getResources().getColor(R.color.white);
//        dataSet.setColors(colors,this);

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter(pieChart));
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
    }


}
