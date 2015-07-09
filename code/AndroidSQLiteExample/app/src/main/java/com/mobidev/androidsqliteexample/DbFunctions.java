package com.mobidev.androidsqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by lawrence on 4/18/15.
 */
public class DbFunctions {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DbFunctions(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    public ArrayList<FilterSpinner> fetchAndroidVersion() {
        ArrayList<FilterSpinner> android = new ArrayList<FilterSpinner>();

        Cursor cursor = database.query(DatabaseHelper.TRAINEES,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FilterSpinner model = new FilterSpinner(cursor.getString(1), cursor.getString(2));
            android.add(model);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return android;
    }

    public ArrayList<FilterSpinner> fetchAndroidVersionRemote() {
        ArrayList<FilterSpinner> android = new ArrayList<FilterSpinner>();

        Cursor cursor = database.query(DatabaseHelper.ANDROID_VERSIONS_REMOTE,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FilterSpinner model = new FilterSpinner(cursor.getString(1), cursor.getString(2));
            android.add(model);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return android;
    }


}
