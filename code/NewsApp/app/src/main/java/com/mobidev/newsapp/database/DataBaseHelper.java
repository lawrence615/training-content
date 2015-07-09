package com.mobidev.newsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.mobidev.newsapp.AppConstants.AppConstants;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "DataBaseHelper";
    private static final int DB_VERSION = AppConstants.DATABASE_VERSION;
    private static final String DB_NAME = AppConstants.DATABASE_NAME;

    private Context mContext;

    public interface Tables {
        String NEWS = "news";
    }

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + Tables.NEWS + " (" + BaseColumns._ID
                            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + AppContract.NewsColumns.NEWS_ID + " integer(11), "
                            + AppContract.NewsColumns.NEWS_TYPE_ID + " integer(11), "
                            + AppContract.NewsColumns.SLUG + " text not null, "
                            + AppContract.NewsColumns.TITLE + " text not null, "
                            + AppContract.NewsColumns.CONTENT + " text not null, "
                            + AppContract.NewsColumns.IMAGE_URL + " text not null, "
                            + AppContract.NewsColumns.CREATED_AT + " varchar(50), unique("
                            + AppContract.NewsColumns.NEWS_ID + ") ON CONFLICT REPLACE" + ");"

            );

        } catch (SQLiteException exception) {
            Log.v(DEBUG_TAG, exception.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Toast.makeText(mContext, "Updating database",
        // Toast.LENGTH_LONG).show();
        Log.w(DEBUG_TAG,
                "Upgrading database. Existing contents will be lost. ["
                        + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + Tables.NEWS);
        onCreate(db);
    }

}
