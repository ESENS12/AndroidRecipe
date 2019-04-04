package com.example.horizontalscrollviewsample;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.Format;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout asthmaActionPlan, controlledMedication, asNeededMedication,
            rescueMedication, yourSymtoms, yourTriggers, wheezeRate, peakFlow, myNewSample, innerlayout;
    LinearLayout.LayoutParams params, textParams;
    LinearLayout next, prev, gridViewItem;
    Button btn_add;
    private final static String TAG = MainActivity.class.getSimpleName();
    int viewWidth;
    GestureDetector gestureDetector = null;
    CustomScrollView horizontalScrollView;
    ArrayList<LinearLayout> layouts;
    int parentLeft, parentRight;
    int mWidth;
    int currPosition, prevPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_backup);

        prev = (LinearLayout) findViewById(R.id.prev);
        next = (LinearLayout) findViewById(R.id.next);

        layouts = new ArrayList<LinearLayout>();
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMoreView();
            }
        });

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.grid_item, null);
        gridViewItem = (LinearLayout) gridView.findViewById(R.id.gridItemView);

        Display display = getWindowManager().getDefaultDisplay();
        mWidth = display.getWidth(); // deprecated
        viewWidth = mWidth / 3;

        horizontalScrollView = (CustomScrollView) findViewById(R.id.hsv);
        horizontalScrollView.setOnFlingListener(new CustomScrollView.OnFlingListener() {
            @Override
            public void onFlingStarted() {
                //Log.d(TAG, "onFlingStarted!");
            }
            //item width = 692 * 40(maxIndex) = 27680
            //layouts size = 43(start 0 , 좌우 양 끝 garbage view 1개씩 해서 +3)

            @Override
            public void onFlingStopped(int currPostion) {
                int index = getVisibleViews();
                //Log.d(TAG, "onFlingStopped! : " + index);
                //Log.d(TAG, "currPostion! : " + currPostion);
                //Log.d(TAG,"getLeft : " + layouts.get(index).getLeft());
                //Log.d(TAG,"layouts child : " +layouts.size());
                horizontalScrollView.smoothScrollTo(layouts.get(index).getLeft(), 0);
//                int myIndex = (int)currPostion/692;
//                Log.d(TAG,"myIndex : " +myIndex);
//                horizontalScrollView.smoothScrollTo(692*myIndex, 0);


            }
        });

        innerlayout = (LinearLayout) horizontalScrollView.getChildAt(0);
        createMoreView();
    }

    public int getVisibleViews() {
        Rect hitRect = new Rect();
        int position = 0;
        for (int i = 0; i < layouts.size(); i++) {
            try{
                LinearLayout ll = (LinearLayout)horizontalScrollView.getChildAt(0);
                if (ll.getChildAt(i).getLocalVisibleRect(hitRect)) {
                        position = i;
                        break;
                    }
            }catch (Exception e){
                Log.e(TAG,"EXCEPTION!" + e.toString());
            }

        }
        //Log.d(TAG,"getVisibleViews Position : " + position);
        return position;
    }

    public void createMoreView(){
        //Log.d(TAG,"viewWIdth : "  +viewWidth);
        params = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        textParams = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i=0; i<=42; i++){

            TextView tv = new TextView(this);
            TextView tv2 = new TextView(this);
            double startData = (double) 1.8;
            double commaData = (double) (i * 2) / 10;
            double savedData = (double) startData + commaData;
            savedData = Math.round(savedData*10)/10.0;

            //textParams.width = (mWidth / 3)/2 ;
            textParams.gravity = Gravity.CENTER;

            tv.setLayoutParams(textParams);
            tv2.setLayoutParams(textParams);

            //tv.setText("" + savedData+"m");
            tv.setText(""+i);

            if(savedData == 10.2 || savedData == 1.8)tv.setText("");

            //tv.setText(""+i+".5m");
            //tv.measure(viewWidth/2 , LinearLayout.LayoutParams.WRAP_CONTENT);
            tv2.setText("m");
            tv2.measure(viewWidth/2 , LinearLayout.LayoutParams.WRAP_CONTENT);

            tv.setGravity(Gravity.CENTER);
            //tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            //tv.setBackgroundColor(getResources().getColor(R.color.white));
            //tv2.setBackgroundColor(getResources().getColor(R.color.white));

            tv2.setGravity(Gravity.CENTER);
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

            tv.setTextSize(46f);
            tv2.setTextSize(32f);
            tv.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextColor(getResources().getColor(R.color.white));
            //LinearLayout grid = new LinearLayout(this);
            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(params);
            //ll.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            ll.setBackground(getResources().getDrawable(R.drawable.box_line_top_1));
            ll.addView(tv);
            //ll.addView(tv2);

            innerlayout.addView(ll);

            layouts.add(ll);
        }
    }
}

