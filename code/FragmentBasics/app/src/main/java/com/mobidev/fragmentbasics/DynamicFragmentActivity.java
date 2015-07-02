package com.mobidev.fragmentbasics;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by lawrence on 7/2/15.
 */
public class DynamicFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFragOne, btnFragTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.placeholder, new FragmentOne())
                .commit();


        btnFragOne = (Button) findViewById(R.id.fragment_one);
        btnFragOne.setOnClickListener(this);

        btnFragTwo = (Button) findViewById(R.id.fragment_two);
        btnFragTwo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_one:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.placeholder, new FragmentOne())
                        .commit();
                break;
            case R.id.fragment_two:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.placeholder, new FragmentTwo())
                        .commit();
                break;
        }
    }
}
