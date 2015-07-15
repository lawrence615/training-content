package com.mobidev.newsapp.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobidev.newsapp.R;
import com.mobidev.newsapp.materialtabhost.MaterialTab;
import com.mobidev.newsapp.materialtabhost.MaterialTabHost;
import com.mobidev.newsapp.materialtabhost.MaterialTabListener;
import com.mobidev.newsapp.ui.activities.NewsSlidePagerActivity;
import com.mobidev.newsapp.ui.fragments.NewsListFragment;
import com.nineoldandroids.view.ViewHelper;


public class MainActivity extends FragmentActivity implements MaterialTabListener, NewsListFragment.OnNewsItemSelectedListener {

    private static final String newsTypes[] = {"News", "Counties",
            "Business", "Sports", "Entertainment"};
    private static final int newsTypesIds[] = {1, 2, 3, 4, 5};


    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
        pager = (ViewPager) findViewById(R.id.pager);

        // init view pager
        adapter = new ViewPagerAdapter(this
                .getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(tabHost.newTab().setText(adapter.getPageTitle(i))
                    .setTabListener(this));

        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public void onNewsItemSelected(int itemId, int news_type_id) {
        Intent slideintent = new Intent(getApplicationContext(),
                NewsSlidePagerActivity.class);

        slideintent.putExtra(NewsSlidePagerActivity.ARG_ITEM_ID, itemId);
        slideintent.putExtra(NewsSlidePagerActivity.ARG_NEWS_TYPE_ID, news_type_id);

        startActivity(slideintent);
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            return new NewsListFragment().newInstance(newsTypesIds[num]);
        }

        @Override
        public int getCount() {
            return newsTypes.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return newsTypes[position];
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
