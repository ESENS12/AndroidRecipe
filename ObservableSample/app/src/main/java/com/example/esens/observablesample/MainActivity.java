package com.example.esens.observablesample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.esens.observablesample.dummy.DummyContent;

import org.w3c.dom.Text;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements itemFragment.OnListFragmentInteractionListener {

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d(TAG,"item :" + item.toString());
    }

    private static final String TAG = "ESNES::"+MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment itemFragment = com.example.esens.observablesample.itemFragment.newInstance();

        manager.beginTransaction().replace(R.id.container,itemFragment).commit();

        Observable<String> simpleObservable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Message From Observer");
                subscriber.onCompleted();
            }
        });

        simpleObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"Error ! " + e.getMessage());
            }

            @Override
            public void onNext(String content) {
                Log.d(TAG,"onNext");
                ((TextView) findViewById(R.id.tv_content)).setText(content);
            }
        });

        //OnError, onCompleted 등을 전부 Override 하지 않아도 된다.
        /*simpleObservable
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String text) {
                        ((TextView) findViewById(R.id.textView)).setText(text);
                    }
                });*/


    }
}
