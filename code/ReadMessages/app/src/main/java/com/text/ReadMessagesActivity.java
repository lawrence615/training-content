package com.text;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.widget.TextView;


public class ReadMessagesActivity extends ActionBarActivity {

    private TextView txtMessages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtMessages = (TextView) findViewById(R.id.messages);
        Uri uriSMSURI = Uri.parse("content://sms/inbox");

        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, BaseColumns._ID + " DESC LIMIT 40");
        String sms = "";
        while (cur.moveToNext()) {
            sms += "From :" + cur.getString(2) + " : " + cur.getString(12) + "\n";
        }
        txtMessages.setText(sms);
    }
}