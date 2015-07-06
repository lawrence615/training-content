package com.mobidev.customlayoutlistview;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ArrayList<CountyModel> countiesList;
    private ListView lvCounties;
    private MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCounties = (ListView) findViewById(R.id.my_listView);

        countiesList = new ArrayList<CountyModel>();
        //populating our arraylist with counties data
        countiesList.add(new CountyModel("Nairobi", "Evans Kidero", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Machakos", "Dr. Alfred Mutua", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Mombasa", "Ali Hassan Joho", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Kisumu", "Jack Ranguma", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Nakuru", "Kinithia Mbugua", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Makueni", "Kivutha Kibwana", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Bungoma", "Hon Kenneth Makello Lusaka", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Bomet", "Issac Kiprono Ruto", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Embu", "Martin Wambora", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Garissa", "Nathif Jama Haddan", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Narok", "Samuel Kuntai Tunai", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Homabay", "Cyprian Otieno Awiti", getResources().getString(R.string.dummy_desc)));
        countiesList.add(new CountyModel("Kajiado", "David Ole Nkedianye", getResources().getString(R.string.dummy_desc)));


        adapter = new MyListAdapter(getApplicationContext(), countiesList);
        lvCounties.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvCounties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //launch a new activity with the county name
                Intent intent_details = new Intent(getApplicationContext(), CountyDetailsActivity.class);
                intent_details.putExtra(CountyDetailsActivity.COUNTY_NAME, countiesList.get(position).getCountyName());
                startActivity(intent_details);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
