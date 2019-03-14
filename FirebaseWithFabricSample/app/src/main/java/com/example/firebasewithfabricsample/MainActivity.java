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
        //build config에서 debuggable d
        boolean isDebuggable =  ( 0 != ( getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
        if(BuildConfig.DEBUG){

        }else{
            Fabric.with(this, new Crashlytics());
            overFlow();
        }

        setContentView(R.layout.activity_main);

    }


    //오류유발 함수
    private void overFlow(){
        this.overFlow();
    }
}
