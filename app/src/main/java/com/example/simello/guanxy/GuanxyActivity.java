package com.example.simello.guanxy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class GuanxyActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        /*
        if (isOnline())
        {
            //@Todo
            //Registrazione per il primo login o exit
        }
        else
        {
            //@Todo
            //Farlo connettere
            //Idea, prendo dal db i dati (quindi punti, user e giorni mancanti) e li inserisco in un Bundle, così
            //possiamo portarli in giro
        }
    */






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

        ImageButton aiutaGliAltri = (ImageButton)findViewById(R.id.aiutaGliAltri);
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
            Toast.makeText(this,"Premuto",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            overridePendingTransition(0, 0);
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
