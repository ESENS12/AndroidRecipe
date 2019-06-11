package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

public class DBhelper {

    private Context context;
    private String db_name;
    private int db_version;
    private DbOpenHelper mDBHelper;
    public static SQLiteDatabase mDB;

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this.context = context;
    }

    public DBhelper(Context context, String name, int version) {
        this.context = context;
        this.db_name = name;
        this.db_version = version;
    }

    public static class Entry implements BaseColumns {

        public static final String TBL_CONTACT = "CONTACT_T";
        public static final String COL_NO = "NUM";
        public static final String COL_NAME = "NAME";
        public static final String COL_PHONE = "PHONE";

        public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_CONTACT + " " +
                "(" +
                COL_NO + " INTEGER NOT NULL" + ", " +
                COL_NAME + " TEXT" + ", " +
                COL_PHONE + " TEXT" + ", " + ")";

        public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_CONTACT;

        public static final String SQL_SELECT = "SELECT * FROM " + TBL_CONTACT;

        public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_CONTACT + " " +
                "(" + COL_NO + ", " + COL_NAME + ", " + COL_PHONE + ", " + ") VALUES ";

        public static final String SQL_DELETE = "DELETE FROM " + TBL_CONTACT;

    }

    private class DbOpenHelper extends SQLiteOpenHelper {

        public DbOpenHelper(){
            super(context, db_name, null, db_version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Entry.SQL_CREATE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void testDB() {
            SQLiteDatabase db = getReadableDatabase();

        }

        public DbOpenHelper open() throws SQLException {
            mDBHelper = new DbOpenHelper();
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
         * @param contact       전화번호
         * @param email         이메일
         * @return              SQLiteDataBase에 입력한 값을 insert
         */
        public long insertColumn(String name, String contact, int num) {
            ContentValues values = new ContentValues();
            values.put(Entry.COL_NAME, name);
            values.put(Entry.COL_PHONE, contact);
            values.put(Entry.COL_NO, num);
            return mDB.insert(Entry.TBL_CONTACT, null, values);
        }

        /**
         * 기존 데이터베이스에 사용자가 변경할 값을 입력하면 값이 변경됨(업데이트)
         * @param id            데이터베이스 아이디
         * @param name          이름
         * @param contact       전화번호
         * @param email         이메일
         * @return              SQLiteDataBase에 입력한 값을 update
         */
        public boolean updateColumn(String name, String contact, long id,int num) {
            ContentValues values = new ContentValues();
            values.put(Entry.COL_NAME, name);
            values.put(Entry.COL_PHONE, contact);
            values.put(Entry.COL_NO, num);
            return mDB.update(Entry.TBL_CONTACT, values, "_id="+id, null) > 0;
        }

        //입력한 id값을 가진 DB를 지우는 메소드
        public boolean deleteColumn(long id) {
            return mDB.delete(Entry.TBL_CONTACT, "_id=" + id, null) > 0;
        }

        //입력한 전화번호 값을 가진 DB를 지우는 메소드
        public boolean deleteColumn(String number) {
            return mDB.delete(Entry.TBL_CONTACT, "contact="+number, null) > 0;
        }

        //커서 전체를 선택하는 메소드
        public Cursor getAllColumns() {
            return mDB.query(Entry.TBL_CONTACT, null, null, null, null, null, null);
        }

        //ID 컬럼 얻어오기
        public Cursor getColumn(long id) {
            Cursor c = mDB.query(Entry.TBL_CONTACT, null,
                    "_id="+id, null, null, null, null);

            //받아온 컬럼이 null이 아니고 0번째가 아닐경우 제일 처음으로 보냄
            if (c != null && c.getCount() != 0)
                c.moveToFirst();

            return c;
        }

        //이름으로 검색하기 (rawQuery)
        public Cursor getMatchName(String name) {

            return mDB.rawQuery( "Select * from address where name" + "'" + name + "'", null);

        }

    }

}

