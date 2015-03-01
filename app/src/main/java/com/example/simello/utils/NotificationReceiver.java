package com.example.simello.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Sunfury & Simello on 26/02/15.
 */
public class NotificationReceiver extends BroadcastReceiver
{
    /**
     * Metodo che verrà usato una volta ricevuta la notifica.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("Notifica", "onReceive from DEFAULT - " + getClass().getSimpleName());

        exec(context,intent);
    }

    //todo da finire una volta ricevuta e accettata la notifica (ma viene accettata subito dopo aver premuto sulla notifica?)
    /**
     * Prendiamo l'utente e il messaggio passato dalla Notifica e facciamo apparire ???????
     * @param context
     * @param intent
     */
    protected void exec(Context context, Intent intent)
    {
        String user = intent.getStringExtra("User");
        String message = intent.getStringExtra("Message");



    }



}
