package com.example.horizontalscrollviewsample;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout asthmaActionPlan, controlledMedication, asNeededMedication,
            rescueMedication, yourSymtoms, yourTriggers, wheezeRate, peakFlow, myNewSample, innerlayout;
    LinearLayout.LayoutParams params;
    LinearLayout next, prev;
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

//        prev = (LinearLayout) findViewById(R.id.prev);
//        next = (LinearLayout) findViewById(R.id.next);

        layouts = new ArrayList<LinearLayout>();
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMoreView();
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        mWidth = display.getWidth(); // deprecated
        viewWidth = mWidth / 3;
        //innerlayout = (LinearLayout)findViewById(R.id.innerLay);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv);
        innerlayout = (LinearLayout)horizontalScrollView.getChildAt(0);
        createMoreView();
        //Log.d("asd","asd");

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


    //todo 동적으로 추가한 view 에만 index가 잡힌다..

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

            TextView tv = new TextView(this);
            tv.setText(""+i);
            tv.setTextSize(20f);

            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(params);
            ll.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            params.gravity = Gravity.CENTER_HORIZONTAL;
            tv.setLayoutParams(params);
            ll.addView(tv);
            innerlayout.addView(ll);
            layouts.add(ll);
        }
    }



}

