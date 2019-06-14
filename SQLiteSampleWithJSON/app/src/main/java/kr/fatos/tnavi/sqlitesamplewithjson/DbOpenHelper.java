package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "test_1.db";
    private static final int DATABASE_VERSION = 1;
    private final String TAG = "DBHelper :: ";
    public static SQLiteDatabase mDB;
    private DataBaseHelper mDBHelper;
    private Context mCtx;

    private class DataBaseHelper extends SQLiteOpenHelper {

        /**
         * 데이터베이스 헬퍼 생성자
         * @param context   context
         * @param name      Db Name
         * @param factory   CursorFactory
         * @param version   Db Version
         */
        public DataBaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Entry.SQL_CREATE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //업데이트를 했는데 DB가 존재할 경우 onCreate를 다시 불러온다
            db.execSQL("DROP TABLE IF EXISTS " + Entry.TBL_CONTACT);
            onCreate(db);
        }
    }

    public static class Entry implements BaseColumns {

        public static final String TBL_CONTACT = "CONTACT_T";
        public static final String COL_NO = "NUM";
        public static final String COL_NAME = "NAME";
        public static final String COL_PHONE = "PHONE";

        public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_CONTACT + " " +
                "(" +
                _ID + " integer primary key autoincrement, " +
                COL_NO + " INTEGER NOT NULL" + ", " +
                COL_NAME + " TEXT" + ", " +
                COL_PHONE + " INTEGER" + ")";

        public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_CONTACT;

        public static final String SQL_SELECT = "SELECT * FROM " + TBL_CONTACT;

        public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_CONTACT + " " +
                "(" + COL_NO + ", " + COL_NAME + ", " + COL_PHONE + ", " + ") VALUES ";

        public static final String SQL_DELETE = "DELETE FROM " + TBL_CONTACT;

    }


    //DbOpenHelper 생성자
    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    //Db를 여는 메소드
    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DataBaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    //Db를 다 사용한 후 닫는 메소드
    public void close() {
        mDB.close();
    }

    /**
     *  데이터베이스에 사용자가 입력한 값을 insert하는 메소드
     * @param name          이름
     * @param phone       전화번호
     * @param no         번호
     * @return              SQLiteDataBase에 입력한 값을 insert
     */
    public long insertColumn(String name, String phone, int no) {
        ContentValues values = new ContentValues();
        values.put(Entry.COL_NAME, name);
        values.put(Entry.COL_PHONE, phone);
        values.put(Entry.COL_NO, no);
        return mDB.insert(Entry.TBL_CONTACT, null, values);
    }

    /**
     * 기존 데이터베이스에 사용자가 변경할 값을 입력하면 값이 변경됨(업데이트)
     * @param id            데이터베이스 아이디
     * @param name          이름
     * @param phone       전화번호
     * @param no         이메일
     * @return              SQLiteDataBase에 입력한 값을 update
     */
    public boolean updateColumn(long id, String name, String phone, int no) {
        ContentValues values = new ContentValues();
        values.put(Entry.COL_NAME, name);
        values.put(Entry.COL_PHONE, phone);
        values.put(Entry.COL_NO, no);
        return mDB.update(Entry.TBL_CONTACT, values, "_id="+id, null) > 0;
    }


    public boolean deleteColumn(long id) {
        return mDB.delete(Entry.TBL_CONTACT, "_id=" + id, null) > 0;
    }

    public boolean deleteColumn(String number) {
        return mDB.delete(Entry.TBL_CONTACT, "contact="+number, null) > 0;
    }


    //string 검색어로 컬럼 삭제
    public boolean deleteColumn(String entity, String search) {
        Cursor c = mDB.query(Entry.TBL_CONTACT, null,
                entity + "= "+"'"+search+"'", null, null, null, null);

        if (c != null && c.getCount() != 0)
            c.moveToFirst();

        return deleteColumn(c.getInt(0));
    }

    public Cursor getAllColumns() {
        return mDB.query(Entry.TBL_CONTACT, null, null, null, null, null, null);
    }


    public Cursor getColumn(long id) {

        Cursor c = mDB.query(Entry.TBL_CONTACT, null,
                "_id="+id, null, null, null, null);
        //받아온 컬럼이 null이 아니고 0번째가 아닐경우 제일 처음으로 보냄
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    //이름으로 검색하기 (rawQuery)
    public Cursor getMatchName(String search) {
        String[] entity = new String[1];
        entity[0] = search;
        Cursor c = mDB.rawQuery( "Select * from " + Entry.TBL_CONTACT + " where NAME = ?" , entity , null);
        return c;
    }

}


