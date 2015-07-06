package com.mobidev.spinerexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lawrence on 4/18/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "android";
    public static final String ANDROID_VERSIONS = "android_versions";
    public static final String ANDROID_VERSIONS_REMOTE = "android_versions_remote";

    public static String COL_ID = "_id";
    public static String COL_VERSION_NAME = "android_version_name";
    public static String COL_VERSIONS = "android_versions";

    private static final int DATABASE_VERSION = 1;

    // database seed data
    public static final String ANDROID_OS_TABLE_SEED = "INSERT INTO '"
            + ANDROID_VERSIONS
            + "' SELECT '1' AS '"
            + COL_ID
            + "', 'Cupcake' AS '"
            + COL_VERSION_NAME
            + "', 'Android 1.5' AS '"
            + COL_VERSIONS
            + "' UNION SELECT '2', 'Donut', 'Android 1.6' UNION SELECT '3', 'Eclair', 'Android 2.0 & 2.1' UNION SELECT '4', 'Froyo', 'Android 2.2' UNION SELECT '5', 'Gingerbread', 'Android 2.3' UNION SELECT '6', 'Honeycomb', 'Android 3.0, 3.1 & 3.2' UNION SELECT '7', 'Ice Cream Sandwich', 'Android 4.0' UNION SELECT '8', 'Jelly Bean', 'Android 4.1, 4.2 & 4.3' UNION SELECT '9', 'KitKat', 'Android 4.4' UNION SELECT '10', 'Lolipop', 'Android 5.0 & 5.1'";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + ANDROID_VERSIONS + "(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "android_version_name VARCHAR(40), " +
                        "android_versions VARCHAR(40));");

        db.execSQL(
                "CREATE TABLE " + ANDROID_VERSIONS_REMOTE + "(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "android_version_name VARCHAR(40), " +
                        "android_versions VARCHAR(40));");


        // insert initial data
        db.execSQL(ANDROID_OS_TABLE_SEED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ANDROID_VERSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + ANDROID_VERSIONS_REMOTE);
        onCreate(db);
    }
}
