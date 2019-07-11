package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.fatos.tnavi.sqlitesamplewithjson.data.DataObject;
import kr.fatos.tnavi.sqlitesamplewithjson.data.ViewHolder;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ListBtnClickListener, View.OnClickListener {

    final String TAG = MainActivity.class.getSimpleName() + "::ESENS";
    DbOpenHelper dBhelper;
    private EditText[] mEditTexts;
    private ListView mListView;
    private ArrayList<DataObject> mInfoArr;
    private Cursor mCursor;
    private DataObject mInfoClass;
    private CustomAdapter mAdapter;
    private Button btnAdd, btnDelete, btnUpdate, btnSearch;
    private ViewHolder lastChoice;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBhelper = new DbOpenHelper(this.getApplicationContext());

        setLayout();

        prefs = getSharedPreferences("kr.fatos.tnavi.sqlitesamplewithjson", MODE_PRIVATE);

        try{
            dBhelper.open();
        }catch (Exception e){
            Log.e(TAG,"EXCEPTION : " + e.toString());
        }

        // 초기 실행인 경우
        if (prefs.getBoolean("firstrun", true)) {
            SharedPreferences.Editor editor = prefs.edit();
            //첫 실행 flag 전환
            editor.putBoolean("firstrun", false);
            editor.apply();
            dBhelper.excuteRawQuery(DbOpenHelper.Entry.SQL_DROP_TBL);
            dBhelper.reOpen();
        }


        //hardcoded sample data
//        dBhelper.insertColumn("ESENS", "01011223344", 1);

        mInfoArr = new ArrayList<DataObject>();

        CursorToArray();

        mAdapter = new CustomAdapter(this, mInfoArr);
        mAdapter.setListBtnClickListener(this);

        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:{

                dBhelper.insertColumn(
                        mEditTexts[0].getText().toString().trim(),
                        mEditTexts[1].getText().toString().trim(),
                        Integer.parseInt(mEditTexts[2].getText().toString())
                );

//                dBhelper.getMatchName(mEditTexts[0].getText().toString().trim());

                }
                break;

            case R.id.btnDelete:{

                ListItemDelete();

                }
                break;

            case R.id.btnSearch:
                dBhelper.ShowAllColumnsLog();
                break;

            case R.id.btnUpdate:
                ListItemUpdate();
                break;
        }

        onDataChanged();
    }

    public void ListItemUpdate(){

        long _id = getItemId();

        //리스트가 비어있는경우 실패 얼럿 후 리턴
        if(_id < 0){
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean b_isUpdate = dBhelper.updateColumn(_id,
                //name
                mEditTexts[0].getText().toString().trim(),
                //phone
                mEditTexts[1].getText().toString().trim(),
                //no
                Integer.parseInt(mEditTexts[2].getText().toString()));

        if(b_isUpdate){
            onDataChanged();
            Toast.makeText(this, "Update!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public long getItemId(){
        long _id = -1;
        if(lastChoice != null){
            _id = dBhelper.selectColumn(Integer.parseInt(lastChoice.no.getText().toString()));
        }else{
            //없으면 리스트 첫번째 아이템에서 검색.
            if(mListView.getChildCount() != 0 ){
                RelativeLayout _rl = (RelativeLayout)mListView.getChildAt(0);
                int no = Integer.parseInt(((TextView)((RelativeLayout)_rl.getChildAt(0)).getChildAt(2)).getText().toString());
                _id = dBhelper.selectColumn(no);
            }

        }
        return _id;
    }

    public void ListItemDelete(){

        long _id = getItemId();

        if(_id < 0) {
            Toast.makeText(MainActivity.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
            return;
        }

        //delete
        if(dBhelper.deleteColumn(_id)){
            Toast.makeText(MainActivity.this, "DELETE ITEM", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onListBtnClick(ViewHolder holder) {
//        mAdapter.notifyDataSetChanged();

        lastChoice = holder;
        dBhelper.selectColumn(Integer.parseInt(holder.no.getText().toString()));

        onDataChanged();
    }

    private void setLayout() {
        mEditTexts = new EditText[]{
                (EditText) findViewById(R.id.et_name),
                (EditText) findViewById(R.id.et_contact),
                (EditText) findViewById(R.id.et_num)
        };

        mListView = (ListView) findViewById(R.id.list);

        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        btnSearch.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    private void onDataChanged(){

        dBhelper.updateAllColumnsId();

        mInfoArr.clear();
        CursorToArray();
//        mAdapter.setArrayList(mInfoArr);
        mAdapter.notifyDataSetChanged();
        //Cursor 닫기
        mCursor.close();
    }

    @Override
    protected void onDestroy() {
        if(dBhelper!=null){
            dBhelper.close();
        }
        super.onDestroy();
    }

    private void CursorToArray() {

        mCursor = null;
        mCursor = dBhelper.getAllColumns();
        Log.d(TAG, "Count = " + mCursor.getCount());

        while (mCursor.moveToNext()) {
            //InfoClass에 입력된 값을 압력
            mInfoClass = new DataObject(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("NAME")),
                    mCursor.getString(mCursor.getColumnIndex("PHONE")),
                    mCursor.getInt(mCursor.getColumnIndex("NUM"))
            );

            mInfoArr.add(mInfoClass);
        }
        //Cursor 닫기
        mCursor.close();
    }

}
