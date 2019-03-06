package com.example.seekbartest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

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
