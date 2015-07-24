package com.mobidev.widgetexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Lawrence on 7/24/15.
 */
public class SaveMeReceiver extends BroadcastReceiver {

    public SaveMeReceiver() {

    }


    /**
     * whenever an event occurs Android calls onReceive() method on all registered broadcast receivers
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Received. React now!", Toast.LENGTH_SHORT).show();
    }
}
