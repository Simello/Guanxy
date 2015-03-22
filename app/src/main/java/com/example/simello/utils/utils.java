package com.example.simello.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.R;

/**
 * Created by Sunfury & Simello on 03/01/15.
 */
public class utils
{
    static boolean notFirstTime = false;
    static private String mPhoneNumber;


    /**
     * Metodo per il controllo se il dispositivo è Online
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();

    }




    /**
     * Ritorna il numero di telefono del dispositivo
     * @param context context per ottenere il numero
     * @return
     */
    public static String numeroTelefonoCorrente(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
               return prefs.getString("numeroTelefono","");

    }

    public static String pin(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("PIN","");

    }


    public static void connect(Context context)
    {
        final Context mContext = context;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        AlertDialog builder = alertDialog.create();

        if(builder.isShowing())
        {
            builder.dismiss();
        }
        else {

            // Setting Dialog Title
            alertDialog.setTitle(mContext.getResources().getString(R.string.connessioneOff));

            if(!notFirstTime)
            {
                // Setting Dialog Message
                alertDialog.setMessage(mContext.getResources().getString(R.string.gpsOffText1) + " " + User.getUser().getNickname() + mContext.getResources().getString(R.string.connectOff1));
                notFirstTime = true;
            }
            else
            {

                alertDialog.setMessage(mContext.getResources().getString(R.string.gpsOffText1) + " " + User.getUser().getNickname() + mContext.getResources().getString(R.string.connectOffNoHello));
            }

            // On pressing Settings button
            alertDialog.setPositiveButton(mContext.getResources().getString(R.string.impostazioniDati), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                    mContext.startActivity(intent);
                }
            });

            alertDialog.setNegativeButton(mContext.getResources().getString(R.string.impostazioniWifi), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);

                    mContext.startActivity(intent);
                }
            });

            alertDialog.setCancelable(false);
            // Showing Alert Message
            alertDialog.show();
        }
    }



    public static void connectNoUsername(Context context)
    {
        final Context mContext = context;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        AlertDialog builder = alertDialog.create();

        if(builder.isShowing())
        {
            builder.dismiss();
        }
        else {

            // Setting Dialog Title
            alertDialog.setTitle(mContext.getResources().getString(R.string.connessioneOff));

            if(!notFirstTime)
            {
                // Setting Dialog Message
                alertDialog.setMessage(mContext.getResources().getString(R.string.gpsOffText1) + " Utente "  + mContext.getResources().getString(R.string.connectOff1));
                notFirstTime = true;
            }
            else
            {

                alertDialog.setMessage(mContext.getResources().getString(R.string.gpsOffText1) + " Utente " + mContext.getResources().getString(R.string.connectOffNoHello));
            }

            // On pressing Settings button
            alertDialog.setPositiveButton(mContext.getResources().getString(R.string.impostazioniDati), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    mContext.startActivity(intent);
                }
            });

            alertDialog.setNegativeButton(mContext.getResources().getString(R.string.impostazioniWifi), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    mContext.startActivity(intent);
                }
            });

            alertDialog.setCancelable(false);
            // Showing Alert Message
            alertDialog.show();
        }
    }


    public static void connect(boolean hasFocus, Context cnt)
    {
        if(hasFocus)
        {
            if(!isConnected(cnt))
                connect(cnt);
        }
    }


    public static void connectNoUser(boolean hasFocus, Context cnt)
    {
        if(hasFocus)
        {
            if(!isConnected(cnt))
                connectNoUsername(cnt);
        }

    }



    /**
     * Metodo per il controllo del GPS quando viene perso il focus alla finestra
     * @param hasFocus
     * @param cnt
     */
    public static void GPSConnect(boolean hasFocus, Context cnt)
    {
        // TODO Auto-generated method stub
        if (hasFocus)
        {
            //Controlla il GPS ogni volta che cambia il focus
            GPSManager gpsManager = new GPSManager(cnt);
            if (!gpsManager.canGetLocation())
                gpsManager.showSettingsAlert();
        }
    }


    //Per il metodo dell'uso degli script php/connessioni http, usano tutti l'AsyncTask
    //Con onPreExecute() -> ProgessDialog con messaggio "in Creazione"
    //doInBackground() -> Esecuzione dello script vero e proprio
    //onPostExecute() -> Fine del dialog
    //private ProgressDialog pDialog;
    //Provo nella main class
    //Dopo circa 10 minuti di ricerca, ho trovato che si può utilizzare l'asynctask anche nei fragment
    //nel onCreateView eseguo un metodo nuovo tipo "eseguoAsyncTask()"
    //Questo metodo  avrà new MyAsyncTask(getActivity(),"Altri campi").execute("");
    //nel mio asyncTask ora avrò il costruttore con i parametri "Altri Campi" e poi i 3 metodi dell'asynctask
    //infine nell'onCreateView avrò il return.
}


