package com.example.jihyun.memo1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jihyun.memo1.BasicInfo;

/**
 * 메모 데이터베이스
 */
public class MemoDatabase {

    public static final String TAG = "MemoDatabase";

    private static MemoDatabase database;
    public static String TABLE_MEMO = "MEMO";
    public static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;
    private Context context;

    private MemoDatabase(Context context) {
        this.context = context;
    }


    public static MemoDatabase getInstance(Context context) {
        if (database == null) {
            database = new MemoDatabase(context);
        }

        return database;
    }

    public boolean open() {
        println("opening database [" + BasicInfo.DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }


    public void close() {
        println("closing database [" + BasicInfo.DATABASE_NAME + "].");
        db.close();

        database = null;
    }

    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }



    /**
     * Database Helper inner class
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, BasicInfo.DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            println("creating database [" + BasicInfo.DATABASE_NAME + "].");

            // TABLE_MEMO
            println("creating table [" + TABLE_MEMO + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_MEMO;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_MEMO + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  INPUT_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "  CONTENT_TEXT TEXT DEFAULT '', "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }


        }

        public void onOpen(SQLiteDatabase db)
        {
            println("opened database [" + BasicInfo.DATABASE_NAME + "].");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");



        }
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }
}