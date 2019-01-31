package com.example.esens.observablesample.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.esens.observablesample.R;
import com.example.esens.observablesample.inteface.Observer;
import com.example.esens.observablesample.inteface.Subject;

import java.util.ArrayList;
import java.util.List;

public class ObserverFragment extends Fragment implements Subject , View.OnClickListener {


    private ArrayList<Observer> observers;
    private ToggleButton button;
    private final static String TAG = "ESENS::" + ObserverFragment.class.getSimpleName();

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.list_item):
                Log.d(TAG,"item click! ");
                break;
        }
    }


    public ObserverFragment() {
        observers = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayout ll_item = (LinearLayout) view.findViewById(R.id.list_item);

        ll_item.setOnClickListener(this);


    }



    @Override
    public void register(Observer observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (final Observer observer : observers){
            observer.update(true);
        }

    }
}
