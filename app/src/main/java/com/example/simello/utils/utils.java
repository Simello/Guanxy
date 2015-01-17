package com.example.simello.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Sunfury & Simello on 03/01/15.
 */
public class utils
{
    //Metodo per il controllo se il dispositivo è connesso Online
    public static boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
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
