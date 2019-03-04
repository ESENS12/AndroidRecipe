package com.example.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    String null_string = null;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        textView = (TextView)findViewById(R.id.tv_null);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "TEST_ID");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "TEST_NAME");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "TEST_CONTENT");
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        mFirebaseAnalytics.setCurrentScreen(this,"firstActivity","not changed");
        final Button btn_crush = (Button)findViewById(R.id.btn_crush);

        btn_crush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackOverflow();
                //btn_crush.setText(textView.getText());
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "CRUSH_ID");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "CRUSH_NAME");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "CRUSH_CONTENT");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });


        //FirebaseAnalytics.getInstance()
    }

    public void stackOverflow() {
        this.stackOverflow();
    }

}
