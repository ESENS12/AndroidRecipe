package com.example.esens.sharedpreferencessample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewAnimator;

/**
 *  SettingActivity를 불러와서 사용 하는 예, Setting에서 preferences 를 활용해서 옵션 저장
 *  필요하다면 setting 메뉴의 preferences item 을 수정하거나 메뉴를 수정하여 사용
 *  sharedpreferences를 활용해서 first launch 감지 및 데이터 CRUD (SettingsActivity의 preferences로 활용중)
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ESENS" + MainActivity.class.getSimpleName();
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.esens.sharedpreferencessample", MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //setupActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            //첫 실행 시 prefs에 저장한다.
            prefs.edit().putBoolean("firstrun", false).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_Setting:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  //액션바 상단에 홈 버튼(<-) 추가
        }else Log.e(TAG,"ActionBar is null");
    }

}
