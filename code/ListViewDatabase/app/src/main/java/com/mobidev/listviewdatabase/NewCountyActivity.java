package com.mobidev.listviewdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lawrence on 4/18/15.
 */
public class NewCountyActivity extends AppCompatActivity {

    private EditText edCountyName, edCountyGovernour, edCountyDesc;
    private Button btnSubmit;

    private DbFunctions dbFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_county);

        dbFunctions = new DbFunctions(getApplicationContext());
        dbFunctions.open();

        /**
         * allows you to go back to the parent activity. In this case our parent activity is MainActivity
         * You need to define the Parent Activity in the manifest
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         * initialize components
         */
        edCountyName = (EditText) findViewById(R.id.county_name_input);
        edCountyGovernour = (EditText) findViewById(R.id.county_governour_input);
        edCountyDesc = (EditText) findViewById(R.id.county_description_input);
        btnSubmit = (Button) findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we validate the inputs first and make sure they are not null
                if (Validate()) {


                    //capture input data and store in variables
                    String county_name = edCountyName.getText().toString().trim();
                    String county_governour = edCountyGovernour.getText().toString().trim();
                    String county_desc = edCountyDesc.getText().toString().trim();


                    if (dbFunctions.createCounty(county_name, county_governour, county_desc)) {
                        //successfully  saved county, redirect back to the main activity
                        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        //data was not successdully saved
                    }
                }
            }
        });

    }

    private boolean Validate() {
        /**
         * if this input field is empty, we request the user to provide a value
         */
//        edCountyName.getText().length() == 0
        if (TextUtils.isEmpty(edCountyName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter county name", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * if this input field is empty, we request the user to provide a value
         */
        if (edCountyGovernour.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please provide name of county governour", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * if this input field is empty, we request the user to provide a value
         */
        if (edCountyDesc.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please give a brief description of the county", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
