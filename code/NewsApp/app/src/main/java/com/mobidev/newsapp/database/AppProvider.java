package com.mobidev.newsapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.mobidev.newsapp.utils.SelectionBuilder;

public class AppProvider extends ContentProvider {

    private static final String DEBUG_TAG = "AppProvider";

    private DataBaseHelper dbHelper;

    private static final int NEWS = 100;
    private static final int NEWS_ID = 110;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);

        switch (match) {
            default: {
                final SelectionBuilder builder = buildExpandedSelection(uri, match);

                Cursor cursor = builder.where(selection, selectionArgs).query(db,
                        false, projection, sortOrder, null);
                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.v(DEBUG_TAG, "insert(uri=" + uri + ", values=" + values.toString());
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case NEWS: {
                db.insertOrThrow(DataBaseHelper.Tables.NEWS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return AppContract.News.buildPostUri(values.getAsString(AppContract.News.NEWS_ID));
            }

            default: {
                throw new UnsupportedOperationException("Unknown insert uri: "
                        + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        return 0;
    }


    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();

        switch (match) {
            case NEWS: {
                return builder.table(DataBaseHelper.Tables.NEWS);
            }
            case NEWS_ID: {
                final String newsId = AppContract.News.getNewsId(uri);
                return builder.table(DataBaseHelper.Tables.NEWS).where(AppContract.News._ID + "=?", newsId);
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

    }

    /**
     * Build and return a {@link android.content.UriMatcher} that catches all {@link android.net.Uri}
     * variations supported by this {@link android.content.ContentProvider}.
     */
    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AppContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "news", NEWS);
        matcher.addURI(authority, "news/*", NEWS_ID);

        return matcher;
    }

}
