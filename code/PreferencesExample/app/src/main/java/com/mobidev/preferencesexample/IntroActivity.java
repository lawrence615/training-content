package com.mobidev.preferencesexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

/**
 * Created by lawrence on 5/15/15.
 */
public class IntroActivity extends AppCompatActivity {

    IntroFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        IntroFragmentAdapter mAdapter = new IntroFragmentAdapter(
                getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (LinePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }

    public void openMain(View v) {
        switch (v.getId()) {
            case R.id.btn_get_started:
                Intent splash = new Intent(getApplicationContext(), MainActivity.class);
                splash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(splash);
                PrefUtils.init(getApplicationContext());
                PrefUtils.markWelcomeDone(this);
                finish();
                break;
        }
    }

}
