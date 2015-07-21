package com.mobidev.widgetexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lawrence on 7/20/15.
 */
public class MainActivity extends AppCompatActivity {

    private EditText edPhone, edMessage;
    private Button btnSaveSmsSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);


        /**
         * initializing components
         */
        edPhone = (EditText) findViewById(R.id.phone_number);
        edMessage = (EditText) findViewById(R.id.message);

        /**
         * display any settings saved earlier
         */
        displaySmsSettings();


        btnSaveSmsSettings = (Button) findViewById(R.id.savesmssettings);
        btnSaveSmsSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSmsSettings();
            }
        });
    }

    protected void saveSmsSettings() {

        if (validateSmsSettingsFields()) {
            SharedPreferences sp = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            sp.edit().putInt(PrefsConstants.PHONE_NO_KEY, Integer.parseInt(edPhone.getText().toString())).commit();
            sp.edit().putString(PrefsConstants.MESSAGE_KEY, edMessage.getText().toString()).commit();


            Toast.makeText(getApplicationContext(), "settings saved", Toast.LENGTH_SHORT).show();
        }

    }

    protected void displaySmsSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        int phone = prefs.getInt(PrefsConstants.PHONE_NO_KEY, 0);
        String message = prefs.getString(PrefsConstants.MESSAGE_KEY, null);

        if (phone != 0) {
            edPhone.setText(Integer.toString(phone));
        }

        if (message != null) {
            edMessage.setText(message);
        }
    }

    private boolean validateSmsSettingsFields() {
        if (TextUtils.isEmpty(edPhone.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(edMessage.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter message", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
