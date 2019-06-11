package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

public class DBhelper extends SQLiteOpenHelper {

    private Context context;

    public static class Entry implements BaseColumns {

        public static final String TBL_CONTACT = "CONTACT_T" ;
        public static final String COL_NO = "NO" ;
        public static final String COL_NAME = "NAME" ;
        public static final String COL_PHONE = "PHONE" ;

        // CREATE TABLE IF NOT EXISTS CONTACT_T (NO INTEGER NOT NULL, NAME TEXT, PHONE TEXT, OVER20 INTEGER)
        public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_CONTACT + " " +
                "(" +
                COL_NO +        " INTEGER NOT NULL" +   ", " +
                COL_NAME +      " TEXT"             +   ", " +
                COL_PHONE +     " TEXT"             +   ", " + ")" ;

        // DROP TABLE IF EXISTS CONTACT_T
        public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_CONTACT ;

        // SELECT * FROM CONTACT_T
        public static final String SQL_SELECT = "SELECT * FROM " + TBL_CONTACT ;

        // INSERT OR REPLACE INTO CONTACT_T (NO, NAME, PHONE, OVER20) VALUES (x, x, x, x)
        public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_CONTACT + " " +
                "(" + COL_NO + ", " + COL_NAME + ", " + COL_PHONE + ", " + ") VALUES " ;

        // DELETE FROM CONTACT_T
        public static final String SQL_DELETE = "DELETE FROM " + TBL_CONTACT ;

    }

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }


}

