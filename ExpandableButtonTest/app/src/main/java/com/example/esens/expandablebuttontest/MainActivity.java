package com.example.esens.expandablebuttontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ESENS::" + MainActivity.class.getSimpleName();
    RelativeLayout container_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        Button btn_goPopMenu = (Button)findViewById(R.id.btn_goPopMenu);
        Button pop_button1 = (Button)findViewById(R.id.pop_button1);
        Button pop_button2 = (Button)findViewById(R.id.pop_button2);
        Button pop_button3 = (Button)findViewById(R.id.pop_button3);
        Button pop_button4 = (Button)findViewById(R.id.pop_button4);


        btn_goPopMenu.setOnClickListener(this);
        pop_button1.setOnClickListener(this);
        pop_button2.setOnClickListener(this);
        pop_button3.setOnClickListener(this);
        pop_button4.setOnClickListener(this);

        container_button = (RelativeLayout)findViewById(R.id.container_button);
    }

    private void goPopupMenu(){
        if(container_button!=null){
            if(container_button.getVisibility()== View.GONE){
                container_button.setVisibility(View.VISIBLE);
            } else if (container_button.getVisibility() == View.VISIBLE){
                container_button.setVisibility(View.GONE);
            }
        }else Log.e(TAG,"container_button layout is null..");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_goPopMenu:
                Log.d(TAG,"btn_goPopMenu Click!");
                goPopupMenu();
                break;
            case R.id.pop_button1:
                Log.d(TAG,"pop_button1 Click!");
                break;
            case R.id.pop_button2:
                Log.d(TAG,"pop_button2 Click!");
                break;
            case R.id.pop_button3:
                Log.d(TAG,"pop_button3 Click!");
                break;
            case R.id.pop_button4:
                Log.d(TAG,"pop_button4 Click!");
                break;

        }
    }
}
