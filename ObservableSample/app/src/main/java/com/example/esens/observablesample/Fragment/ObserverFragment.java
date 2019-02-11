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

public class ObserverFragment extends Fragment implements Subject{


    private ArrayList<Observer> observers;
    private ToggleButton button;
    private final static String TAG = "ESENS::" + ObserverFragment.class.getSimpleName();


    public ObserverFragment() {
        observers = new ArrayList<>();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
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
