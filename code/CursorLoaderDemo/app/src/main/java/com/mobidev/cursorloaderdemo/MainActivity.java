package com.mobidev.cursorloaderdemo;

import android.app.ListActivity;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.app.LoaderManager.LoaderCallbacks;
import android.provider.Browser;
import android.view.Menu;

import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Take some time to go through these links
 *
 * @see http://javarticles.com/2015/06/android-simplecursoradapter-example.html
 * @see http://codetheory.in/asynchronous-background-execution-and-data-loading-with-loaders-framework-in-android/
 */

public class MainActivity extends ListActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "CursorLoaderDemo.MainActivity";

    SimpleCursorAdapter mAdapter;

    String mSearchType;

    Integer year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, null,
                new String[]{"Title", "URL"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(mAdapter);


        /**
         * initiate the loader manager
         *
         * LoaderManager also manages Loader objects
         */

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * A Loader is an object which knows its data source through the ContentProvider and is designed to asynchronously load
     * data and monitor the underlying data source
     * <p/>
     * <p/>
     * We identify a loader by its ID
     *
     * @param id
     * @param stuff
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle stuff) {
//        Uri baseUrl = Browser.BOOKMARKS_URI;
//        System.err.println("uURI... " + baseUrl);
        Uri baseUrl = Uri.parse("content://com.android.chrome.browser/bookmarks");
        return new CursorLoader(getApplicationContext(), baseUrl, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        System.err.println("counting ... " + data.getCount());
        mAdapter.swapCursor(data);//bind cursor data to the listitem
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}