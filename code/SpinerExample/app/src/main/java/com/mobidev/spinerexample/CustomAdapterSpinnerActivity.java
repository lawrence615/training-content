package com.mobidev.spinerexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrence on 5/6/15.
 */
public class CustomAdapterSpinnerActivity extends ActionBarActivity {

    private Spinner mySpinner;
    private MySpinnerAdapter adapter;
    private String selected_os_version;
    private Button btnSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter_spinner);

        mySpinner = (Spinner) findViewById(R.id.mySpinner);
        btnSelected = (Button) findViewById(R.id.getSelected);

        final ArrayList<FilterSpinner> data = new ArrayList<FilterSpinner>();
        data.add(new FilterSpinner("Cupcake", "Android 1.5"));
        data.add(new FilterSpinner("Donut", "Android 1.6"));
        data.add(new FilterSpinner("Eclair", "Android 2.0 & 2.1"));
        data.add(new FilterSpinner("Froyo", "Android 2.2"));
        data.add(new FilterSpinner("Gingerbread", "Android 2.3"));
        data.add(new FilterSpinner("Honeycomb", "Android 3.0, 3.1 & 3.2"));
        data.add(new FilterSpinner("Ice Cream Sandwich", "Android 4.0"));
        data.add(new FilterSpinner("Jelly Bean", "Android 4.1, 4.2 & 4.3"));
        data.add(new FilterSpinner("KitKat", "Android 4.4"));
        data.add(new FilterSpinner("Lolipop", "Android 5.0 & 5.1"));

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


}
