package com.mobidev.spinerexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private ListView lvList;
    private String[] spinerTypes = {"Simple Spinner", "Store Selected Item", "Custom Adapter Spinner", "SQLite Data Spinner", "MySQL Data Spinner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvList = (ListView) findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_data, spinerTypes);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent newIntent = null;
                switch (position) {
                    case 0:
                        newIntent = new Intent(getApplicationContext(), SimpleSpinnerActivity.class);
                        break;
                    case 1:
                        newIntent = new Intent(getApplicationContext(), StoreSelectedItemActivity.class);
                        break;
                    case 2:
                        newIntent = new Intent(getApplicationContext(), CustomAdapterSpinnerActivity.class);
                        break;

                    case 3:
                        newIntent = new Intent(getApplicationContext(), SQLiteDataSpinnerActivity.class);
                        break;
                    case 4:
                        newIntent = new Intent(getApplicationContext(), MysqlDataSpinnerActivity.class);
                        break;
                    default:
                        break;
                }

                if (newIntent != null) {
                    startActivity(newIntent);
                }


            }
        });


    }


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
