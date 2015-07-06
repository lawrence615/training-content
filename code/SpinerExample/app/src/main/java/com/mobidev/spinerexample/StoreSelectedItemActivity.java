package com.mobidev.spinerexample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by lawrence on 4/30/15.
 */
public class StoreSelectedItemActivity extends ActionBarActivity {

    private Spinner mySpinner;
    private int selected_os_position;
    private String[] androidOSVersionsArray = {"Alpha (1.0)", "Beta (1.1)", "Cupcake (1.5)", "Donut (1.6)", "Eclair (2.0–2.1)", "Froyo (2.2–2.2.3)", "Gingerbread (2.3–2.3.7)", "Honeycomb (3.0–3.2.6)", "Ice Cream Sandwich (4.0–4.0.4)", "Jelly Bean (4.1–4.3.1)", "KitKat (4.4–4.4.4, 4.4W–4.4W.2)", "Lollipop (5.0–5.1)"};

    private static final String ARG_SELECTED_OS = "pref_selected_os";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_spinner);

        mySpinner = (Spinner) findViewById(R.id.mySpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_simple_spinner_item, androidOSVersionsArray);
        adapter.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_os_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        /**
         * Read the previously selected position
         */
        if (readSelectedOs() != 0) {
            mySpinner.setSelection(readSelectedOs());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * Store selected position before leaving the activity
         */
        saveSelectedOS(selected_os_position);

    }


    protected void saveSelectedOS(int position) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        sp.edit().putInt(ARG_SELECTED_OS, position).commit();
    }

    protected int readSelectedOs() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());


        return sp.getInt(ARG_SELECTED_OS, 0);
    }


}
