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
        //cd.setStartAngle(-180f);
        cd.setValueWidthPercent(55f);
        cd.setTextSize(36f);
        cd.setColor(Color.BLUE);
        cd.setDrawText(true);
        //내부 데이터 알파값 , 0 주면 데이터만 표시됨
        cd.setDimAlpha(10);
        cd.setDrawInnerCircle(true);
        cd.setFormatDigits(1);
        cd.setTouchEnabled(false);
        String[] text = new String[2];

        text[0] = "asdasdad";
        text[1] = "12341234";
        //cd.setDrawText(true);
        //cd.setSelectionListener(this);
        cd.setUnit("%");

        //custom text array length = totalValue / stepSize
        cd.setStepSize(50f);
        cd.setCustomText(text);
        // cd.setCustomText(...); // sets a custom array of text
        cd.showValue(75f, 100f, true);
        //CircleDisplay cd = new CircleDisplay(this);

    }
}
