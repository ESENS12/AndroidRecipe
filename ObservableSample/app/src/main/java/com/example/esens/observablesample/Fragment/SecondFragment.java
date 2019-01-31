package com.example.esens.observablesample.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esens.observablesample.R;
import com.example.esens.observablesample.inteface.Observer;

public class SecondFragment extends Fragment implements Observer{
    private final static String TAG = "ESENS::" + SecondFragment.class.getSimpleName();

    @Override
    public void update(boolean checked) {
        Log.d(TAG,"Secondfragment Updated!" + checked);
    }
}
