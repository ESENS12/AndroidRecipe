package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBhelper = new DbOpenHelper(this.getApplicationContext());

        setLayout();

        try{
            dBhelper.open();
        }catch (Exception e){
            Log.e(TAG,"EXCEPTION : " + e.toString());
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

                dBhelper.getMatchName(mEditTexts[0].getText().toString().trim());

                }
                break;

            case R.id.btnDelete:
                break;

            case R.id.btnSearch:
                break;

            case R.id.btnUpdate:
                break;
        }

        onDataChanged();
    }

    @Override
    public void onListBtnClick(ViewHolder holder) {
//        mAdapter.notifyDataSetChanged();

        //index는 Autoincrement 인덱스값이므로(만약 key를 따로 매핑한다면 다른 방식으로 쿼리를 넘겨줘야함)
//        if(dBhelper.deleteColumn(holder.index+1)){
//            Toast.makeText(MainActivity.this, "DELETE ITEM : "+holder.index, Toast.LENGTH_SHORT).show();
//        }else if(dBhelper.deleteColumn(DbOpenHelper.Entry.COL_NAME, holder.name.getText().toString())){
//            Toast.makeText(MainActivity.this, "DELETE ITEM : "+holder.index, Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(MainActivity.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
//        }
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

        mInfoArr.clear();
        CursorToArray();
        mAdapter.setArrayList(mInfoArr);
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
        Log.i(TAG, "Count = " + mCursor.getCount());

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
