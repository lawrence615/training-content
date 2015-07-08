package com.mobidev.listviewphpmysqlexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button btnUniversities, btnColleges, btnPolytechnics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUniversities = (Button) findViewById(R.id.universities);
        btnUniversities.setOnClickListener(btnClickListener);
        btnColleges = (Button) findViewById(R.id.colleges);
        btnColleges.setOnClickListener(btnClickListener);
        btnPolytechnics = (Button) findViewById(R.id.polytechnics);
        btnPolytechnics.setOnClickListener(btnClickListener);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.universities:
                    Intent intent = new Intent(getApplicationContext(), UniversitiesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.colleges:
                    break;
                case R.id.polytechnics:
                    break;
                default:
                    break;
            }
        }
    };


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
