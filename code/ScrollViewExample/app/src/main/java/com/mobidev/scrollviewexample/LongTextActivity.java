package com.mobidev.scrollviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lawrence on 7/6/15.
 */
public class LongTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_text);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
