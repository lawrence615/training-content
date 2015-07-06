package com.mobidev.listviewdatabase;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by lawrence on 4/18/15.
 */
public class EditCountyActivity extends ActionBarActivity{

    public static final String SELECTED_COUNTY_ID = "county_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_county);
    }
}
