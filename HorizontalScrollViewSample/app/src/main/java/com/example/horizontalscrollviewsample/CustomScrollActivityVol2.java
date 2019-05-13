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
    TextView tv_left , tv_center, tv_right;
    int viewWidth;
    ArrayList<LinearLayout> layouts;
    int mWidth;
    int currPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customscrollvol2);

        tv_left = (TextView)findViewById(R.id.tv_left);
        tv_center = (TextView)findViewById(R.id.tv_center);
        tv_right = (TextView)findViewById(R.id.tv_right);

        layouts = new ArrayList<LinearLayout>();
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMoreView();
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
                if(Math.abs(x-y) > viewWidth/2){
                    Log.d(TAG,"Changed index!");
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            runOnUiThread(new Runnable(){
//                                @Override
//                                public void run() {
//                                    //m_layoutMap.setVisibility(View.VISIBLE);
//                                }
//                            });
//                        }
//                    }).start();
                }
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
            }
        });

        innerlayout = (LinearLayout) sc.getChildAt(0);
        //createMoreView();
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

    // add garbage data
    // 0.5 단위로 tv에 값을 보여주고, 0.1 단위는 view 로 대체
    public void createMoreView(){

        //Log.d(TAG,"viewWIdth : "  +viewWidth);
        params = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        textParams = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);


//        for (int i=0; i<=88; i++){
//
//            TextView tv = new TextView(this);
//            TextView tv2 = new TextView(this);
//            double startData = (double) 1.6;
//            double commaData = (double) (i * 1) / 10;
//            double savedData = (double) startData + commaData;
//            savedData = Math.round(savedData*10)/10.0;
//
//            textParams.gravity = Gravity.CENTER;
//
//            tv.setLayoutParams(textParams);
//            tv2.setLayoutParams(textParams);
//            tv.setText("|");
//
//            if(savedData >= 10.1 || savedData <= 1.9) tv.setText("");
//
//            tv2.setText("m");
//            tv2.measure(viewWidth/2 , LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            tv.setGravity(Gravity.CENTER);
//            tv2.setGravity(Gravity.CENTER);
//            tv2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
//
//            tv.setTextSize(46f/getResources().getDisplayMetrics().density);
//            tv2.setTextSize(32f);
//            tv.setTextColor(getResources().getColor(R.color.white));
//            tv2.setTextColor(getResources().getColor(R.color.white));
//            //LinearLayout grid = new LinearLayout(this);
//            LinearLayout ll = new LinearLayout(this);
//
//            ll.setLayoutParams(params);
//            //ll.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            ll.setBackground(getResources().getDrawable(R.drawable.box_line_top_1));
//            ll.addView(tv);
//            //ll.addView(tv2);
//
//            innerlayout.addView(ll);
//            layouts.add(ll);
//        }
        int left = 0;
        int center = 1;
        int right = 2;

        for(int j =0; j<44; j++){
            bottomTextParams = new LinearLayout.LayoutParams(viewWidth*3, LinearLayout.LayoutParams.WRAP_CONTENT);
            bottomTextParams.gravity = Gravity.BOTTOM;
            bottomTextParams.setMargins(0,0,0,10);
            TextView bottmtv1 = new TextView(this);

            if(left == j ){
                left += 3;
                bottmtv1.setGravity(Gravity.LEFT);
                bottomTextParams.setMargins(10,0,0,10);
            }
            if (center == j){
                center += 3;
                bottmtv1.setGravity(Gravity.CENTER);
                bottomTextParams.setMargins(0,0,0,10);
            }
            if(right == j){
                right += 3;
                bottmtv1.setGravity(Gravity.RIGHT);
                bottomTextParams.setMargins(0,0,10,10);
            }

            bottmtv1.setLayoutParams(bottomTextParams);
            bottmtv1.setBackground(getResources().getDrawable(R.drawable.box_line_top_1));

//
//            /***
//             * 0,3,6,9
//             * 1,4,7,10
//             * 2,5,8,11
//             * */
//            double marg = j % 2;
//            if(j == 0 ) bottmtv1.setGravity(Gravity.LEFT);
//
//            if(marg != 0){
//                if(marg > 0){
//                    bottmtv1.setGravity(Gravity.RIGHT);
//                }else{
//                    bottmtv1.setGravity(Gravity.LEFT);
//                }
//            }else{
//                bottmtv1.setGravity(Gravity.RIGHT);
//            }

            //bottmtv1.setBackgroundColor(getResources().getColor(R.color.BlueViolet));
            bottmtv1.setTextColor(getResources().getColor(R.color.white));
            bottmtv1.setText("test");
            bottmtv1.measure(viewWidth*3, LinearLayout.LayoutParams.WRAP_CONTENT);
            innerlayout.addView(bottmtv1);

            //innerlayout.addView(bottmtv1);

        }
    }
}

