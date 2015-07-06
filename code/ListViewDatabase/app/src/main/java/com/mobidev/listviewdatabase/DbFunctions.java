package com.mobidev.listviewdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public boolean createCounty(String county_name, String county_governour, String desc) {
        ContentValues values = new ContentValues();
        values.put("county_name", county_name);
        values.put("county_governour", county_governour);
        values.put("county_desc", desc);

        long insertedId = 0;
        insertedId = database.insert(dbHelper.TABLE_COUNTIES, null,
                values);
        if (insertedId != 0) {
            return true;
        }

        return false;
    }

    public ArrayList<CountyModel> fetchAllCounties() {
        ArrayList<CountyModel> counties = new ArrayList<CountyModel>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_COUNTIES,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CountyModel model = new CountyModel(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            counties.add(model);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return counties;
    }


}
