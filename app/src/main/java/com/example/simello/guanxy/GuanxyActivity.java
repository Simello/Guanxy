package com.example.simello.guanxy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.simello.controller.punteggi.Utente;
import com.example.simello.utils.AsyncConnection;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.utils;

import java.util.HashMap;


public class GuanxyActivity extends ActionBarActivity
{

    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);



        GPSManager gpsManager = new GPSManager(this);

        if(gpsManager.canGetLocation())
        {
            Log.i("GPS",""+ gpsManager.getLatitude());
        }
        else
        {
            gpsManager.showSettingsAlert();
        }

        if (utils.isConnected(this))
        {
            //@Todo
            //Registrazione per il primo login o exit
            String code = prefs.getString("codiceSegreto","codiceSegreto");
            if (code.compareTo("codiceSegreto") == 0)
            {
                //Creazione HashMap Iniziale
                HashMap<String, HashMap<String,String>> registrazione = new HashMap<String, HashMap<String,String>>();
                //Inserisco nella mappa il valore dell'url
                //Chiave -> url
                //Valore -> url reale
                HashMap<String,String> values = new HashMap<String,String>();
                values.put("url","http://192.168.1.64:8080/guanxy/registration/iscrivi");

                //Prendo il numero di telefono
                TelephonyManager tMgr =(TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
                mPhoneNumber = tMgr.getLine1Number();

                //Lo aggiungo alla mappa
                values.put("phone","3208814625");
                AsyncConnection cnt = new AsyncConnection(this);

                //Aggiungo anche l'username
                String userName = prefs.getString("username","");
                values.put("username",userName);

                //Infine lo invio alla classe AsyncConnection
                //La quale richiede una mappa di String,String (Chiave,Valore)

                registrazione.put("Invio",values);
                cnt.execute(registrazione);
            }
            /*
            if utente non registrato, registra utente
            per la prima connessione utilizzare lo shared preferences, dove prima controllo
            se la variabile è != null, se è diversa è già installata,
            altrimenti eseguo script di registrazione isi pisi
            if()
            {


            }
            else
                altrimenti mostra schermata principale

             */


        }
        else
        {
            //@Todo
            //Farlo connettere
            //Idea, prendo dal db i dati (quindi punti, user e giorni mancanti) e li inserisco in un Bundle, così
            //possiamo portarli in giro
            Toast.makeText(this, "Non connesso",Toast.LENGTH_SHORT).show();

        }







        ImageButton chiediAiuto = (ImageButton) findViewById(R.id.chiediAiuto);
        chiediAiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(GuanxyActivity.this, ChiediAiuto.class);
                GuanxyActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);


            }

            ;
        });

        ImageButton aiutaGliAltri = (ImageButton)findViewById(R.id.buttonTextPremi);
        aiutaGliAltri.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v){
                Intent myIntent = new Intent(GuanxyActivity.this, AiutaGliAltri.class);
                GuanxyActivity.this.startActivity(myIntent);
                overridePendingTransition(0,0);
            }
        });




        //REPARTO BOTTONI
        //Prendo il bottone e setto a true x lasciare il colore blue
        //DA RICORDARE
        //Quando si premeranno gli altri bottoni (Punti o Guida) il valore di pressed di Guanxy deve essere messo a false
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        //Prendo il singolo bottone
        final Button punti = (Button) findViewById(R.id.punti);

        final Button guida = (Button) findViewById(R.id.guida);

        //GESTIONE COLORE BOTTONILEL
        guanxy.setPressed(true);
        guanxy.setSelected(true);
        guanxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righ, quando premo Guanxy, cambia il colore di Punti/Guida(da aggiungere)
                punti.setPressed(false);
                punti.setSelected(false);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                guanxy.setSelected(false);
                guanxy.setPressed(false);


                Intent myIntent = new Intent(GuanxyActivity.this, PuntiActivity.class);
                GuanxyActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        guida.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v){
                v.setSelected(true);

                guanxy.setSelected(false);
                guanxy.setPressed(false);

                Intent myIntent = new Intent(GuanxyActivity.this, GuidaActivity.class);
                GuanxyActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settingsGuanxy) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//Serve per sistemare i bottoni quando viene premuto il tasto "back" fisico
    //Per evitare problemi, crea una nuova activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
     //Se sono già a Guanxy, esco
    }




}
