package com.example.esens.cardviewtest;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.esens.cardviewtest.spotdialog.SpotsDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RouteListAdapter.OnItemClicked{

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.left_arrow_btn_id :
                    Log.d(TAG,"left button click!");
                    break ;
                case R.id.right_arrow_btn_id :
                    Log.d(TAG,"right button click!");
                    break;

            }
        }
    }

    private RouteListAdapter routeListAdapter;
    private static ArrayList<RouteCardData> routeList;
    private static final String TAG = "ESENS::" + MainActivity.class.getSimpleName();
    private Button.OnClickListener btn_onClickListener;

    private static int current_item = 0;

    //for Progress Bar
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private int progressStatus = 0;

    BtnOnClickListener onClickListener = new BtnOnClickListener();

    @Override
    public void onRouteListItemClick(int position) {
        Log.d(TAG,"onRouteListItemClick : " + position);
        //selected 되면 색상 변경
        routeListAdapter.setSelected(position);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);


        Drawable icon = getResources().getDrawable(R.drawable.s_info_icon_01);

        RecyclerView routeListRecyclerView = (RecyclerView) findViewById(R.id.list_routelist);

        routeListRecyclerView.setHasFixedSize(true);

        routeListRecyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        //DATA를 감쌀 List 초기화 및 어댑터에 등록
        routeList = new ArrayList<RouteCardData>();
        routeListAdapter = new RouteListAdapter(routeList);
        routeListAdapter.setOnClick(this);

        //뷰에 리스트 어댑터 등록
        routeListRecyclerView.setAdapter(routeListAdapter);

        BlockSnapHelper snapHelper = new BlockSnapHelper(2);
        snapHelper.attachToRecyclerView(routeListRecyclerView);

        snapHelper.setSnapBlockCallback(new BlockSnapHelper.SnapBlockCallback() {
            @Override
            public void onBlockSnap(int snapPosition) { }

            @Override
            public void onBlockSnapped(int snapPosition) {
                //cardView selected item position (ESENS)
                Log.d("onBlockSnapped", ": " + snapPosition);
                select_buttonResource(snapPosition+1);
                updateRouteListPageIndicator(snapPosition+1);

            }
        });

        //DATA 객체 초기화(TEST)
        RouteCardData routeCardData = new RouteCardData();
        routeCardData.setIcon(icon);
        //routeList.add(new RouteCardData(type, color, icon, showCharge, charge, distance, time));


        //TEST DATA 리스트에 3개정도 넣어봄
        routeList.add(routeCardData);
        routeList.add(routeCardData);
        routeList.add(routeCardData);

        //seleted 된놈한테 지정된 color로 배경이 들어감 (map route 와 연동되어있을 듯)
        routeListAdapter.setSelected(0);

        //버튼 핸들러한테 붙여서 사용하면 됨
        ( (RecyclerView)findViewById(R.id.list_routelist) ).scrollToPosition(0);
        routeListAdapter.notifyDataSetChanged();

        //버튼 동적 생성
        create_buttonResource(routeList.size());
        select_buttonResource(1);


        progress_test();
    }


    private void select_buttonResource(int index){
        //1,2,3 3가지 경로 일 때, 0,4번은 btn_arrow 임(시작과 끝)
        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());

        final LinearLayout lm = (LinearLayout) findViewById(R.id.btn_layout);

        for(int i = 1; i < lm.getChildCount()-1; i++){
            Button btn_select = (Button) lm.getChildAt(i);
            btn_select.setWidth(width);
            btn_select.setHeight(height);

            if(i==index){
                btn_select.setTextColor(Color.WHITE);
                btn_select.setBackgroundColor(Color.BLUE);
            }else{
                btn_select.setTextColor(Color.BLACK);
                btn_select.setBackgroundColor(Color.WHITE);
            }
        }

    }
    private void progress_test(){
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    Log.d(TAG,"progressStatus : " + progressStatus);
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void create_buttonResource(int itemSize){
        //btn 동적 생성

        final LinearLayout lm = (LinearLayout) findViewById(R.id.btn_layout);

        // linearLayout params 정의
        LinearLayout.LayoutParams btn_arrow_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams btn_number_params = new LinearLayout.LayoutParams(
                width, height);

        // 버튼 생성
        final ImageButton left_btn = new ImageButton(this);

        left_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp));
        left_btn.setLayoutParams(btn_arrow_params);
        left_btn.setId(R.id.left_arrow_btn_id);
        left_btn.setOnClickListener(onClickListener);
        lm.addView(left_btn);

        left_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_item > 1) {
                    Log.d(TAG,"curr item is bigger then 1 ! move to prev page");
                    //((RecyclerView) findViewById(R.id.list_routelist)).scrollToPosition(current_item-1);
                    select_buttonResource(current_item-1);
                    updateRouteListPageIndicator(current_item-1);
                    //routeListAdapter.notifyDataSetChanged();
                }
            }
        });

        for(int j=0; j<routeList.size(); j++){
            final Button btn_number = new Button(this);
            btn_number.setText(String.valueOf(j+1));
            btn_number.setLayoutParams(btn_number_params);

            switch (j){
                case(0) : btn_number.setId(R.id.btn_number_one);
                        break;
                case(1) : btn_number.setId(R.id.btn_number_two);
                        break;
                case(2) : btn_number.setId(R.id.btn_number_three);
                        break;
                default: btn_number.setId(R.id.btn_number_one);
            }

            btn_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"number button click!");
                    int index = 0;
                    //버튼의 id값과 비교하여 index값 매핑하기
                    switch (v.getId()){
                        case(R.id.btn_number_one) : index = 1;
                            break;
                        case(R.id.btn_number_two) : index = 2;
                            break;
                        case(R.id.btn_number_three) : index = 3;
                            break;

                    }
                    select_buttonResource(index);
                    updateRouteListPageIndicator(index);
                }
            });
            lm.addView(btn_number);

        }

        final ImageButton right_btn = new ImageButton(this);
        right_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp));
        right_btn.setLayoutParams(btn_arrow_params);
        right_btn.setId(R.id.right_arrow_btn_id);
        right_btn.setOnClickListener(onClickListener);
        right_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_item < 4 ) {
                    Log.d(TAG,"curr item is smaller then 4 ! move to next page");
                    //((RecyclerView) findViewById(R.id.list_routelist)).scrollToPosition(current_item+1);
                    select_buttonResource(current_item+1);
                    updateRouteListPageIndicator(current_item+1);
                    //routeListAdapter.notifyDataSetChanged();
                }
            }
        });
        lm.addView(right_btn);
    }

    //snap에 따라서 item position을 전달받는다.
    private void updateRouteListPageIndicator(int itemPosition) {


        switch (itemPosition){
            case 1 :
                current_item = 1;
                Log.d(TAG,"1번");
                break;
            case 2 :
                current_item = 2;
                Log.d(TAG,"2번");
                break;
            case 3 :
                current_item = 3;
                Log.d(TAG,"3번");
                break;
            default: {
                Log.d(TAG,"defalut");
            }
        }

        ((RecyclerView) findViewById(R.id.list_routelist)).scrollToPosition(itemPosition-1);
        //btn 옮기면 됨
    }
}
