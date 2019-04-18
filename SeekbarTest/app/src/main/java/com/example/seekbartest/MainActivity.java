package com.example.seekbartest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sdsmdg.harjot.crollerTest.Croller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar_luminosite);
        final TextView tv = (TextView)findViewById(R.id.tvThumb);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();

                tv.setText("" + progress);

                int padding= seekBar.getPaddingLeft() + seekBar.getPaddingRight();
                int sPos = seekBar.getLeft() + seekBar.getPaddingLeft();
                int xPos = (seekBar.getWidth()-padding) * seekBar.getProgress() / seekBar.getMax() + sPos - (tv.getWidth()/2);

                tv.setX(xPos);
//
//                Croller croller = (Croller) findViewById(R.id.croller);
//                croller.setIndicatorWidth(10);
//                croller.setBackCircleColor(Color.parseColor("#EDEDED"));
//                croller.setMainCircleColor(Color.WHITE);
//                croller.setMax(50);
//                croller.setStartOffset(45);
//                croller.setIsContinuous(false);
//                croller.setLabelColor(Color.BLACK);
//                croller.setProgressPrimaryColor(Color.parseColor("#0B3C49"));
//                croller.setIndicatorColor(Color.parseColor("#0B3C49"));
//                croller.setProgressSecondaryColor(Color.parseColor("#EEEEEE"));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
