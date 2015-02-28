package com.example.simello.guanxy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.utils.AsyncConnection;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;







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

        Position position = new Position((float)gpsManager.getLatitude(),(float) gpsManager.getLongitude());
        List<Position> positions = new ArrayList<Position>();
        positions.add(position);

        //CREAZIONE PRIMO UTENTE
        User user = User.getIstance(prefs.getString("nickname",""), this, "3208814625", 0, positions);
        Log.d("User",user.getNickname());



        if(!gpsManager.canGetLocation())
        {
            gpsManager.showSettingsAlert();
        }

        if (utils.isConnected(this))
        {
            //@Todo
            //Registrazione per il primo login o exit
            String code = prefs.getString("PIN","PIN");
            if (code.compareTo("PIN") == 0)
            {
                HashMap<String,Object> invio = new HashMap<String, Object>();
                invio.put("url","Http://etc.etc");

                invio.put("User",user);

                AsyncConnection cnt = new AsyncConnection(this);

                cnt.execute(invio);

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
            Log.i("Off","Offline");
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

        //GESTIONE COLORE BOTTONI
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


    /**
     * Metodo che controlla la perdita del FOCUS della schermata attuale
     * @param hasFocus
     */
    //@todo Da sistemare l'onResume, deve chiudere per bene il Dialog
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        super.onWindowFocusChanged(hasFocus);
        utils.GPSConnect(hasFocus,this);
    }




}
