package com.mobidev.implicitintents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Button btnCall, btnMail, btnWebsite, btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnCall = (Button) findViewById(R.id.callus);
        btnCall.setOnClickListener(contactsListener);
        btnMail = (Button) findViewById(R.id.mailus);
        btnMail.setOnClickListener(contactsListener);
        btnWebsite = (Button) findViewById(R.id.website);
        btnWebsite.setOnClickListener(contactsListener);
        btnLocation = (Button) findViewById(R.id.location);
        btnLocation.setOnClickListener(contactsListener);

    }



    View.OnClickListener contactsListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callus:
                    Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                            + btnCall.getText().toString().trim()));
                    startActivity(call);
                    break;
                case R.id.mailus:
                    sendEmail();
                    break;
                case R.id.website:
                    openBrowser();
                    break;
                case R.id.location:
                    openMap();
                    break;

                default:
                    break;
            }

        }
    };

    protected void sendEmail() {
        String[] recipients = { btnMail.getText().toString().trim() };
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        email.setType("message/rfc822");

        email.putExtra(Intent.EXTRA_EMAIL, recipients);
        email.putExtra(Intent.EXTRA_SUBJECT, "Namaste");

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email,
                    "Choose an email client from..."));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No email client installed.",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void openBrowser() {
        String url = btnWebsite.getText().toString().trim();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://" + url));
        startActivity(i);
    }

    protected void openMap() {
        String uri = String.format(Locale.ENGLISH,
                "http://maps.google.com/maps?daddr=%f,%f (%s)", -1.2877881,
                36.8047396, "mlab East Africa Offices");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ane) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(uri));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(getApplicationContext(),
                        "Please install a maps application", Toast.LENGTH_LONG)
                        .show();
            }
        }

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
