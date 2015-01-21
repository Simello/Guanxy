package com.example.simello.guanxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sunfury on 20/01/15.
 */
public class ChiediAiuto extends ActionBarActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent myIntent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiedi_aiuto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




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

                //Con queste due righe, quando premo Guanxy, cambia il colore di Punti/Guida(da aggiungere)
                punti.setPressed(false);
                punti.setSelected(false);

                Intent myIntent = new Intent(ChiediAiuto.this, GuanxyActivity.class);

                ChiediAiuto.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                guanxy.setSelected(false);
                guanxy.setPressed(false);


                Intent myIntent = new Intent(ChiediAiuto.this, PuntiActivity.class);
                ChiediAiuto.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        guida.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v){
                v.setSelected(true);

                guanxy.setSelected(false);
                guanxy.setPressed(false);

                Intent myIntent = new Intent(ChiediAiuto.this, GuidaActivity.class);

                ChiediAiuto.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

        Intent myIntent = new Intent(this, GuanxyActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(0, 0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
