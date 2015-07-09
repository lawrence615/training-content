package com.mobidev.androidsqliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lawrence on 4/18/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mlab_db";
    public static final String TRAINEES = "trainees";

    public static String COL_ID = "_id";
    public static String COL_REG_NO = "reg_no";
    public static String COL_LAST_NAME = "last_name";
    public static String COL_FIRST_NAME = "first_name";

    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TRAINEES + "(" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_REG_NO + " INTEGER, " +
                        COL_LAST_NAME + " VARCHAR(40), " +
                        COL_FIRST_NAME + " VARCHAR(40));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRAINEES);
        onCreate(db);
    }
}
