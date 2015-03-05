package com.example.simello.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;


/**
 * Created by Sunfury & Simello on 26/02/15.
 */
public class NotificationReceiver extends ParsePushBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        Log.i("PushEver",""+ sharedPrefs.getString("notifiche",""));
        if (!sharedPrefs.contains("notifiche") || sharedPrefs.getString("notifiche", "").compareTo("true") == 0)
        {
            Log.i("Push",""+sharedPrefs.getString("notifiche",""));
            super.onReceive(context, intent);
        }
    }



}
