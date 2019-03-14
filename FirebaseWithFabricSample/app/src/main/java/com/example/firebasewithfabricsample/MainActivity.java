package com.example.firebasewithfabricsample;

import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isDebuggable =  ( 0 != ( getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );

        if(isDebuggable){
            //debug version.. not use Crashlytics
        }else{
            //release version.. start Crashlytics
            Fabric.with(this, new Crashlytics());
        }

        setContentView(R.layout.activity_main);
        //overFlow();
    }


    //오류유발 함수
    private void overFlow(){
        this.overFlow();
    }
}
