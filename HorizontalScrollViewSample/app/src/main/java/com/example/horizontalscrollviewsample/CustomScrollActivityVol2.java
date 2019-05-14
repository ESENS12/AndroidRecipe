package com.example.horizontalscrollviewsample;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomScrollActivityVol2 extends AppCompatActivity {

    private final static String TAG = CustomScrollActivityVol2.class.getSimpleName();
    ScrollEventView sc ;
    private ViewTreeObserver.OnGlobalLayoutListener VOGL;

    LinearLayout innerlayout;
    LinearLayout.LayoutParams params, textParams, bottomTextParams;
    LinearLayout gridViewItem;
    Button btn_add;
    TextView tv_center;
    int viewWidth;
    ArrayList<LinearLayout> layouts;
    int mWidth;
    int currPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customscrollvol2);

        tv_center = (TextView)findViewById(R.id.tv_center);

        layouts = new ArrayList<LinearLayout>();
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createMoreView();
            }
        });
        VOGL = null;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.grid_item, null);
        gridViewItem = (LinearLayout) gridView.findViewById(R.id.gridItemView);

        Display display = getWindowManager().getDefaultDisplay();
        //mWidth = display.getWidth(); // deprecated
        //viewWidth = mWidth / 20;


        sc = (ScrollEventView) findViewById(R.id.hsv);

        VOGL = new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                View v = (View) findViewById(R.id.hsv);
                viewWidth = v.getWidth()/9;
//                viewWidth += 2;
                createMoreView();
                sc.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        };

        sc.setOnScrollChangedListener(new ScrollEventView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int x, int y) {
                Log.d(TAG,"onScrollChanged (activity)");
            }
        });


        sc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sc.startScrollerTask();
                }
                return false;
            }
        });

        sc.measure(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        sc.getViewTreeObserver().addOnGlobalLayoutListener(VOGL);

        sc.setOnScrollStoppedListener(new ScrollEventView.OnScrollStoppedListener() {
            @Override
            public void onScrollStopped() {
                currPosition = getVisibleViews();

                LinearLayout ll = (LinearLayout) sc.getChildAt(0);

                int left = ll.getChildAt(currPosition).getLeft();
                int right = ll.getChildAt(currPosition).getRight();

                int leftdistance = Math.abs(sc.getScrollX() - left);
                int rightdistance = Math.abs(sc.getScrollX() - right);

                //기준점
                if(leftdistance > rightdistance) {
                    currPosition += 1;
                }

                //반드시 해당 index view의 left를 기준으로 이동해야 함
                sc.smoothScrollTo(ll.getChildAt(currPosition).getLeft(), 0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                double startData = (double) 1.9;
                                double commaData = (double) (currPosition) / 10;
                                double savedData = (double) startData + commaData;
                                final String number = Double.toString(Math.round(savedData*10)/10.0);
                                tv_center.setText(number);
                            }
                        });
                    }
                }).start();
            }
        });

        innerlayout = (LinearLayout) sc.getChildAt(0);
    }

    public int getVisibleViews() {
        Rect hitRect = new Rect();
        int position = 0;
        LinearLayout ll = (LinearLayout)sc.getChildAt(0);
        for (int i = 0; i < layouts.size(); i++) {
            try{
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

    // 0.5 단위로 tv에 값을 보여주고, 0.1 단위는 view 로 대체
    public void createMoreView(){

        params = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        textParams = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);

        int PointIndex = 0;

        for (int i=0; i<=88; i++){

            double startData = (double) 1.5;
            double commaData = (double) (i * 1) / 10;
            double savedData = (double) startData + commaData;
            savedData = Math.round(savedData*10)/10.0;

            bottomTextParams = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            bottomTextParams.gravity = Gravity.BOTTOM;
            bottomTextParams.setMargins(0,0,0,10);
            TextView bottmtv1 = new TextView(this);

            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(params);
            ll.setBackground(getResources().getDrawable(R.drawable.m_s_setting_new5));

            if (PointIndex == i){
                PointIndex += 5;

                bottomTextParams.setMargins(0,0,0,10);
                bottmtv1.setLayoutParams(bottomTextParams);
                bottmtv1.setGravity(Gravity.CENTER);
                bottmtv1.setBackgroundColor(getResources().getColor(R.color.scrollviewBackgroundVol2));
                bottmtv1.setTextColor(getResources().getColor(R.color.white));
                //bottmtv1.measure(viewWidth*3, LinearLayout.LayoutParams.WRAP_CONTENT);

                bottmtv1.setText(savedData+"");
                if(savedData >= 10.4 || savedData <= 1.4) bottmtv1.setText("");

                ll.addView(bottmtv1);
            }

            innerlayout.addView(ll);
            layouts.add(ll);
        }
    }
}

