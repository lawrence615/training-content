package com.mobidev.listviewdatabase;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

    private DbFunctions dbFunctions;

    private ArrayList<CountyModel> countiesList;
    private ListView lvCounties;
    private MyListAdapter adapter;

    private ActionMode mActionMode;
    private int currentListItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbFunctions = new DbFunctions(getApplicationContext());
        dbFunctions.open();

        lvCounties = (ListView) findViewById(R.id.my_listView);
        countiesList = dbFunctions.fetchAllCounties();

        adapter = new MyListAdapter(getApplicationContext(), countiesList);
        lvCounties.setAdapter(adapter);
        lvCounties.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                currentListItemIndex = position;
                mActionMode = startSupportActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });
        adapter.notifyDataSetChanged();

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
        if (id == R.id.action_add) {
            Intent intent_new_county = new Intent(getApplicationContext(), NewCountyActivity.class);
            startActivity(intent_new_county);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_edit:
                    Intent edit_county_intent = new Intent(getApplicationContext(), EditCountyActivity.class);
                    edit_county_intent.putExtra(EditCountyActivity.SELECTED_COUNTY_ID, currentListItemIndex);
                    startActivity(edit_county_intent);
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.menu_delete:
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

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
