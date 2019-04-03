package com.example.horizontalscrollviewsample;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout asthmaActionPlan, controlledMedication, asNeededMedication,
            rescueMedication, yourSymtoms, yourTriggers, wheezeRate, peakFlow, myNewSample, innerlayout;
    LinearLayout.LayoutParams params;
    LinearLayout next, prev, gridViewItem;
    Button btn_add;
    private final static String TAG = MainActivity.class.getSimpleName();
    int viewWidth;
    GestureDetector gestureDetector = null;
    HorizontalScrollView horizontalScrollView;
    ArrayList<LinearLayout> layouts;
    int parentLeft, parentRight;
    int mWidth;
    int currPosition, prevPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_backup);

        prev = (LinearLayout) findViewById (R.id.prev);
        next = (LinearLayout) findViewById (R.id.next);

        layouts = new ArrayList<LinearLayout>();
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMoreView();
            }
        });

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView  = inflater.inflate(R.layout.grid_item, null);
        gridViewItem = (LinearLayout)gridView.findViewById(R.id.gridItemView);

        Display display = getWindowManager().getDefaultDisplay();
        mWidth = display.getWidth(); // deprecated
        viewWidth = mWidth / 3;
        //innerlayout = (LinearLayout)findViewById(R.id.innerLay);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv);
        innerlayout = (LinearLayout)horizontalScrollView.getChildAt(0);
        createMoreView();

        //layouts.add(new LinearLayout(this))

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        horizontalScrollView.smoothScrollTo(
//                                (int) horizontalScrollView.getScrollX()
//                                        + viewWidth,
//                                (int) horizontalScrollView.getScrollY());
//                    }
//                }, 100L);
//            }
//        });
//
//        prev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        horizontalScrollView.smoothScrollTo(
//                                (int) horizontalScrollView.getScrollX()
//                                        - viewWidth,
//                                (int) horizontalScrollView.getScrollY());
//                    }
//                }, 100L);
//            }
//        });
//

        horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollX < oldScrollX) {
                    currPosition = getVisibleViews("left");
                } else {
                    currPosition = getVisibleViews("right");
                }

                Log.d(TAG,"currPosition : " + currPosition);
            }
        });

//        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (gestureDetector.onTouchEvent(event)) {
//                    return true;
//                }
//                return false;
//            }
//        });
    }


    public int getVisibleViews(String direction) {
        Rect hitRect = new Rect();
        int position = 0;
        int rightCounter = 0;
        for (int i = 0; i < layouts.size(); i++) {
            try{
                LinearLayout ll = (LinearLayout)horizontalScrollView.getChildAt(0);
                if (ll.getChildAt(i).getLocalVisibleRect(hitRect)) {
                    if (direction.equals("left")) {
                        position = i;
                        break;
                    } else if (direction.equals("right")) {

                        rightCounter++;
                        position = i;
                        if (rightCounter == 2)
                            break;
                    }
                }
            }catch (Exception e){
                Log.e(TAG,"EXCEPTION!" + e.toString());
            }

        }
        return position;
    }

//
//    public int getVisibleViews(String direction) {
//        Rect hitRect = new Rect();
//        int position = 0;
//
//        for (int i = 0; i < 4; i++) {
//            if (direction.equals("left")) {
//                position = 0;
//            } else if (direction.equals("right")) {
//                position = 4;
//            }
//        }
//        return position;
//    }


    public void createMoreView(){
        params = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i=0; i<10; i++){

//            TextView tv = new TextView(this);
//            LinearLayout ll = gridViewItem;
//
//            //TextView tv = (TextView) gridViewItem.getChildAt(0);
//            tv.setText(""+i);
//            ll.addView(tv);
//            innerlayout.addView(ll);
//            innerlayout.addView(grid);


            TextView tv = new TextView(this);
            TextView tv2 = new TextView(this);

            tv.setText(""+i);
            tv2.setText("m");
            tv.setGravity(Gravity.CENTER);
            tv2.setGravity(Gravity.RIGHT);
            tv.setTextSize(46f);
            tv2.setTextSize(32f);
            tv.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextColor(getResources().getColor(R.color.white));
            //LinearLayout grid = new LinearLayout(this);
            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(params);
            //ll.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            params.gravity = Gravity.CENTER_HORIZONTAL;
            tv.setLayoutParams(params);
            tv2.setLayoutParams(params);
            ll.addView(tv);
            ll.addView(tv2);
            innerlayout.addView(ll);

            layouts.add(ll);
        }
    }



}

