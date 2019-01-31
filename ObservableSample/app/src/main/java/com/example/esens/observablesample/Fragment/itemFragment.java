package com.example.esens.observablesample.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esens.observablesample.MyitemRecyclerViewAdapter;
import com.example.esens.observablesample.R;
import com.example.esens.observablesample.dummy.DummyContent;
import com.example.esens.observablesample.dummy.DummyContent.DummyItem;
import com.example.esens.observablesample.inteface.Observer;
import com.example.esens.observablesample.inteface.Subject;

public class itemFragment extends ObserverFragment implements Observer{


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private final static String TAG = "ESENS::" + itemFragment.class.getSimpleName();
    private TextView item_tv;
    @Override
    public void update(boolean checked) {
        Log.d(TAG,"itemFragment checked! " + checked);
        if(checked){
            item_tv.setText("checked!");

        }else{
            item_tv.setText("UnChecked!");
        }
    }

    public itemFragment() {
    }

    @SuppressWarnings("unused")
    public static itemFragment newInstance() {
        itemFragment fragment = new itemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        item_tv = (TextView)view.findViewById(R.id.item_tv);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyitemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
