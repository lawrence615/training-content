package com.mobidev.fragmentbasics;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStaticFrags, btnDynamicFrags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStaticFrags = (Button) findViewById(R.id.staticfragments);
        btnStaticFrags.setOnClickListener(this);
        btnDynamicFrags = (Button) findViewById(R.id.dynamicfragments);
        btnDynamicFrags.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.staticfragments) {
            startActivity(new Intent(getApplicationContext(), StaticFragmentActivity.class));
        }

        if (v.getId() == R.id.dynamicfragments){
            startActivity(new Intent(getApplicationContext(), DynamicFragmentActivity.class));
        }
    }
}
