package com.mobidev.simplelistviewexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by lawrence on 4/16/15.
 */
public class CountyDetailsActivity extends ActionBarActivity{
    public static String COUNTY_NAME = "county_name";

    private TextView txtMyCounty;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county_details);

        txtMyCounty = (TextView) findViewById(R.id.my_county);

        extras = getIntent().getExtras();
        if(extras != null){
            txtMyCounty.setText(extras.getString(COUNTY_NAME));
        }

    }
}
