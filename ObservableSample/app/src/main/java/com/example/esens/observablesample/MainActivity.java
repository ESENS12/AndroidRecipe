package com.example.esens.observablesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ESNES::"+MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Observable<String> simpleObservable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello this is rxJava");
                subscriber.onCompleted();
            }
        });
    }
}
