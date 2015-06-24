package com.mobidev.navigation.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    /**
     * Boolean indicating whether user has signed in
     */
    public static final String PREF_LOGIN_DONE = "pref_login_done";

    /**
     * Boolean indicating whether we performed the (one-time) welcome flow.
     */
    public static final String PREF_WELCOME_DONE = "pref_welcome_done";


    public static void init(final Context context) {
        // Check what year we're configured for
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public static boolean isLoginDone(final Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_LOGIN_DONE, false);
    }

    public static void markLoginDone(final Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_LOGIN_DONE, true).commit();
    }

    public static boolean isWelcomeDone(final Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_WELCOME_DONE, false);
    }

    public static void markWelcomeDone(final Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_WELCOME_DONE, true).commit();
    }


}
