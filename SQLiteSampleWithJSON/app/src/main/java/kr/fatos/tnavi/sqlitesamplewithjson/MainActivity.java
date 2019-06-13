package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import kr.fatos.tnavi.sqlitesamplewithjson.data.DataObject;
import kr.fatos.tnavi.sqlitesamplewithjson.data.ViewHolder;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ListBtnClickListener {

    final String TAG = MainActivity.class.getSimpleName() + "::ESENS";
    DbOpenHelper dBhelper;
    private EditText[] mEditTexts;
    private ListView mListView;
    private ArrayList<DataObject> mInfoArr;
    private Cursor mCursor;
    private DataObject mInfoClass;
    private CustomAdapter mAdapter;


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
    public void onListBtnClick(ViewHolder holder) {
        Log.d(TAG,"onListBtnClick : " + holder.index);

    }

    private void setLayout() {
        mEditTexts = new EditText[]{
                (EditText) findViewById(R.id.et_name),
                (EditText) findViewById(R.id.et_contact),
                (EditText) findViewById(R.id.et_num)
        };

        mListView = (ListView) findViewById(R.id.list);
    }

    public void btnAdd(View v) {
        //추가를 누를 경우 EditText에 있는 String 값을 다 가져옴
        dBhelper.insertColumn(
                mEditTexts[0].getText().toString().trim(),
                mEditTexts[1].getText().toString().trim(),
                Integer.parseInt(mEditTexts[2].getText().toString())
        );
        //ArrayList 내용 삭제
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
