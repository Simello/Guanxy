package com.example.simello.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Sunfury on 12/03/15.
 */
public class UpdatePositionReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {

        Toast.makeText(context , "I'm running", Toast.LENGTH_SHORT).show();

    }
}
