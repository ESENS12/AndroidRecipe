package com.example.constraintlayouttest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.Placeholder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class PlaceholderActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv1,iv2,iv3;
    Placeholder placeholder;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv1:
                placeholder.setContentId(R.id.iv1);
                break;
            case R.id.iv2:
                placeholder.setContentId(R.id.iv2);
                break;
            case R.id.iv3:
                placeholder.setContentId(R.id.iv3);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeholder);
        placeholder = findViewById(R.id.place_holder);

        iv1 = findViewById(R.id.iv1);
        iv1.setOnClickListener(this);

        iv2 = findViewById(R.id.iv2);
        iv2.setOnClickListener(this);

        iv3 = findViewById(R.id.iv3);
        iv3.setOnClickListener(this);



    }


}
