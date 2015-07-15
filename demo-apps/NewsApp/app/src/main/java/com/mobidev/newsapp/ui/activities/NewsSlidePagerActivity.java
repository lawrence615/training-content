package com.mobidev.newsapp.ui.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.mobidev.newsapp.utils.ParserUtils;
import com.mobidev.newsapp.R;
import com.mobidev.newsapp.ui.fragments.ViewArticleFragment;
import com.mobidev.newsapp.database.AppContract;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lawrence on 4/25/15.
 */
public class NewsSlidePagerActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_NEWS_TYPE_ID = "news_type_id";
    private static final String LOADER_ARG_TAG = "news_type";


    private Bundle extras;
    private int item_id;
    private int newsTypeId;

    private ViewPager mPager;
    private CursorPagerAdapter mCursorPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_slide);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();
        if (extras != null) {
            item_id = extras.getInt(ARG_ITEM_ID);
            newsTypeId = extras.getInt(ARG_NEWS_TYPE_ID);
        }


        mCursorPagerAdapter = new CursorPagerAdapter(
                getSupportFragmentManager(), ViewArticleFragment.class,
                NewsQuery.PROJECTION, null);


        Bundle args = new Bundle();
        args.putInt(LOADER_ARG_TAG, newsTypeId);

        getSupportLoaderManager().initLoader(NewsQuery._TOKEN, args, this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                mPager.setAdapter(mCursorPagerAdapter);
                mPager.setCurrentItem(item_id, true);
            }
        }, 100);
        mPager.setPageTransformer(true, new DepthPageTransformer());
    }


    @Override
    public Loader<Cursor> onCreateLoader(int token, Bundle bundle) {
        if (token == NewsQuery._TOKEN) {
            int newstype = bundle.getInt(LOADER_ARG_TAG);

            ArrayList<String> selectionArgs = new ArrayList<String>();
            ArrayList<String> selectionClauses = new ArrayList<String>();

            if (newstype > 0) {
                selectionClauses.add(AppContract.News.NEWS_TYPE_ID + "=?");
                selectionArgs.add(String.format(Locale.US, "%d", newstype));
            }

            String selection = selectionClauses.isEmpty() ? null : ParserUtils
                    .joinStrings(" AND ", selectionClauses, null);
            String[] args = selectionArgs.isEmpty() ? null : selectionArgs
                    .toArray(new String[0]);

            return new CursorLoader(
                    getApplicationContext(),
                    AppContract.News.CONTENT_URI, NewsQuery.PROJECTION,
                    selection, args, AppContract.News.DEFAULT_SORT);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorPagerAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorPagerAdapter.swapCursor(null);
    }

    public class CursorPagerAdapter<F extends Fragment> extends
            FragmentStatePagerAdapter {
        private final Class<F> fragmentClass;
        private final String[] projection;
        private Cursor cursor;

        /**
         * @param FragmentManager fm
         * @param Class           fragmentClass
         * @param projection
         * @param cursor
         */
        public CursorPagerAdapter(FragmentManager fm, Class<F> fragmentClass,
                                  String[] projection, Cursor cursor) {
            super(fm);
            this.fragmentClass = fragmentClass;
            this.projection = projection;
            this.cursor = cursor;

        }

        @Override
        public F getItem(int position) {
            if (cursor == null)
                return null;

            if (cursor.moveToPosition(position)) {
                F frag;
                try {
                    frag = fragmentClass.newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Bundle args = new Bundle();
                for (int i = 0; i < projection.length; ++i) {

                    args.putString(projection[i], cursor.getString(cursor
                            .getColumnIndex(projection[i])));
                }
                args.putInt("POSITION", position);
                frag.setArguments(args);
                return frag;
            }
            return null;
        }

        @Override
        public int getCount() {
            if (cursor == null)
                return 0;
            else
                return cursor.getCount();
        }

        public void swapCursor(Cursor c) {
            if (cursor == c)
                return;

            this.cursor = c;
            notifyDataSetChanged();
        }

        public Cursor getCursor() {
            return cursor;
        }
    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                ViewHelper.setAlpha(view, 0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                ViewHelper.setAlpha(view, 1);
                ViewHelper.setTranslationX(view, 0);
                ViewHelper.setScaleX(view, 1);
                ViewHelper.setScaleY(view, 1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                ViewHelper.setAlpha(view, 1 - position);

                // Counteract the default slide transition
                ViewHelper.setTranslationX(view, pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                        * (1 - Math.abs(position));
                ViewHelper.setScaleX(view, scaleFactor);
                ViewHelper.setScaleY(view, scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                ViewHelper.setAlpha(view, 0);
            }
        }
    }

    private interface NewsQuery {
        int _TOKEN = 0x1;
        String[] PROJECTION = {BaseColumns._ID,
                AppContract.NewsColumns.NEWS_ID,
                AppContract.NewsColumns.TITLE,
                AppContract.NewsColumns.IMAGE_URL,
                AppContract.NewsColumns.CONTENT,
                AppContract.NewsColumns.CREATED_AT};

    }

}
