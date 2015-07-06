package com.mobidev.simplelistviewexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String[] countiesArray = {"Nairobi", "Machakos", "Mombasa", "Kisumu", "Nakuru", "makueni", "Bungoma",
            "Bomet", "Embu", "Garissa", "Narok", "Homabay", "Kajiado"};

    private ListView lvCars;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.counties_list_item, countiesArray);

        lvCars = (ListView) findViewById(R.id.my_listView);
        lvCars.setAdapter(adapter);

        //listening to single list item on click
        lvCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //selected item
                String county = ((TextView) view).getText().toString();

                //We launch a new activity on selecting single item
                Intent newIntent = new Intent(getApplicationContext(), CountyDetailsActivity.class);
                newIntent.putExtra(CountyDetailsActivity.COUNTY_NAME, county);
                startActivity(newIntent);
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
