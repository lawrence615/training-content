package com.mobidev.listviewdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lawrence on 4/18/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "counties_db";
    public static final String TABLE_COUNTIES = "counties";

    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_COUNTIES + "(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "county_name VARCHAR(40), " +
                        "county_governour VARCHAR(40), " +
                        "county_desc TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTIES);
        onCreate(db);
    }
}
