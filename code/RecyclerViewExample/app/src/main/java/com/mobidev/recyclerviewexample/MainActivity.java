package com.mobidev.recyclerviewexample;


/**
 * @see http://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        /**
         * Default layout manager is LinearLayoutManager, but you can change this to e.g. GridLayoutManager
         */
        mLayoutManager = new LinearLayoutManager(this);
        lLayout = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CodingLoveAdapter(getApplicationContext(), recyclerViewItems());
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<CodingLoveItem> recyclerViewItems() {
        ArrayList<CodingLoveItem> list = new ArrayList<CodingLoveItem>();
        list.add(new CodingLoveItem("When the boss is right behind me", "http://tclhost.com/Q9D8128.gif"));
        list.add(new CodingLoveItem("CTRL + Z", "http://tclhost.com/cxE8MvO.gif"));
        list.add(new CodingLoveItem("Intern telling me he edited my code", "http://tclhost.com/Jf1nAcm.gif"));
        list.add(new CodingLoveItem("Noticing a huge bug during the release", "http://tclhost.com/uJKywOT.gif"));
        list.add(new CodingLoveItem("Monday morning", "http://tclhost.com/vOyleq3.gif"));
        list.add(new CodingLoveItem("Easily fixing a lot of small bugs", "http://tclhost.com/U71EWEw.gif"));
        list.add(new CodingLoveItem("Dealine approaching", "http://tclhost.com/dUktwjv.gif"));
        list.add(new CodingLoveItem("When the sales guy introduces me to the client", "http://tclhost.com/fWb9tHt.gif"));
        list.add(new CodingLoveItem("When the boss gives us our Friday", "http://tclhost.com/NbFm61d.gif"));

        return list;
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

        if (id == R.id.about_app) {
            startActivity(new Intent(getApplicationContext(), AboutAppActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
