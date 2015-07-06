package com.mobidev.spinerexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by lawrence on 5/6/15.
 */
public class MysqlDataSpinnerActivity extends ActionBarActivity {

    private Spinner mySpinner;
    private MySpinnerAdapter adapter;
    private String selected_os_version;
    private Button btnSelected;
    private ArrayList<FilterSpinner> data;
    private DbFunctions dbFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter_spinner);

        dbFunctions = new DbFunctions(getApplicationContext());
        dbFunctions.open();

        data = dbFunctions.fetchAndroidVersionRemote();

        if (data.size() == 0) {

        }
    }
}
