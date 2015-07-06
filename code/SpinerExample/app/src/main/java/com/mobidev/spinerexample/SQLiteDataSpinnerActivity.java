package com.mobidev.spinerexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lawrence on 5/6/15.
 */
public class SQLiteDataSpinnerActivity extends ActionBarActivity{

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

        mySpinner = (Spinner) findViewById(R.id.mySpinner);
        btnSelected = (Button) findViewById(R.id.getSelected);

        dbFunctions = new DbFunctions(getApplicationContext());
        dbFunctions.open();


        data = dbFunctions.fetchAndroidVersion();



        adapter = new MySpinnerAdapter(getApplicationContext(), 0, data);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_os_version = data.get(position).getVersion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Versions: " + selected_os_version, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        dbFunctions.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dbFunctions.close();
        super.onPause();
    }
}
