package com.mobidev.widgetexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Lawrence on 7/20/15.
 */
public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {


    private static final String SAVE_ME_CLICKED = "widget_save_button_clicked";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get all ids
        ComponentName thisWidget = new ComponentName(context, AppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_main);

            remoteViews.setOnClickPendingIntent(R.id.imageView, getPendingSelfIntent(context, SAVE_ME_CLICKED));


            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);//add this line

        if (SAVE_ME_CLICKED.equals(intent.getAction())) {
//            Intent newIntent = new Intent(this, SaveMeReceiver.class);
//            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//            intent.setAction("com.mobidev.widgetexample.BroadcastReceiver");
//            intent.putExtra("command", "SendSMS");
//            sendBroadcast(newIntent);

            sendSMS(context);

        }
    }


    public void sendSMS(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        int number = sp.getInt(PrefsConstants.PHONE_NO_KEY, 0);
        String message = sp.getString(PrefsConstants.MESSAGE_KEY, null);
        if (number != 0 && message != null) {
            SmsManager.getDefault().sendTextMessage("+254" + number, null, message, null, null);
        }
    }


    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


}
