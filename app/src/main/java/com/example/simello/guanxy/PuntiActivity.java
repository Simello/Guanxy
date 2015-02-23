package com.example.simello.guanxy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.simello.controller.varie.User;
import com.example.simello.utils.utils;

/**
 * Created by Sunfury & Simello on 17/01/15.
 */
public class PuntiActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punteggi_fragment);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String userRef = prefs.getString("username","");


        User user = User.getUser();
        //Stampa a video l'username dell'utente
        TextView username = (TextView) findViewById(R.id.user);
        username.setText(user.getNickname());



      

        //REPARTO BOTTONI
        //Prendo il bottone e setto a true x lasciare il colore blue
        //DA RICORDARE
        //Quando si premeranno gli altri bottoni (Punti o Guida) il valore di pressed di Guanxy deve essere messo a false
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        //Prendo il singolo bottone
        final Button punti = (Button) findViewById(R.id.punti);

        final Button guida = (Button) findViewById(R.id.guida);

        //GESTIONE COLORE BOTTONI
        punti.setPressed(true);
        punti.setSelected(true);
        guanxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Guanxy, cambia il colore di Punti/Guida(da aggiungere)
                punti.setPressed(false);
                punti.setSelected(false);

                //Boh l'intent tocca crearlo non so x quale motivo
                //Cmq gli si passa l'activity attuale, e quella a cui voglio arrivare
                Intent myIntent = new Intent(PuntiActivity.this, GuanxyActivity.class);
                //gli dico che se preme BACK FISICO, allora chiude app.
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                //mando in avvio l'activity
                PuntiActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);


            };
        });

        guida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                punti.setSelected(false);
                punti.setPressed(false);

                Intent myIntent = new Intent(PuntiActivity.this, GuidaActivity.class);

                PuntiActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

            };
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        findViewById(R.id.punti).setPressed(false);
        findViewById(R.id.punti).setPressed(false);


        Intent myIntent = new Intent(this, GuanxyActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }

    /**
     * Metodo per evitare l inutilizzo del GPS durante l'esecuzione di Guanxy.
     * Controlla il GPS ogni volta che viene perso il focus alla schermata
     * @param hasFocus
     */
    //@todo Da sistemare l'onResume, deve chiudere per bene il Dialog
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        super.onWindowFocusChanged(hasFocus);
        utils.GPSConnect(hasFocus, this);
    }
}
