package com.example.circledisplaysample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleDisplay cd = (CircleDisplay) findViewById(R.id.circleDisplay);
        cd.setAnimDuration(3000);
        cd.setValueWidthPercent(55f);
        cd.setTextSize(36f);
        cd.setColor(Color.BLUE);
        cd.setDrawText(true);
        cd.setDrawInnerCircle(true);
        cd.setFormatDigits(1);
        cd.setTouchEnabled(true);
        //cd.setSelectionListener(this);
        cd.setUnit("%");
        cd.setStepSize(0.5f);
        // cd.setCustomText(...); // sets a custom array of text
        cd.showValue(75f, 100f, true);
        //CircleDisplay cd = new CircleDisplay(this);

    }
}
