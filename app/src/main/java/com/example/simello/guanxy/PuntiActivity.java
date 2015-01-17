package com.example.simello.guanxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Sunfury on 17/01/15.
 */
public class PuntiActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punteggi_fragment);



        //Prendo la dimensione dello schermo
        int width = getWindowManager().getDefaultDisplay().getWidth();

        //REPARTO BOTTONI
        //Prendo il bottone e setto a true x lasciare il colore blue
        //DA RICORDARE
        //Quando si premeranno gli altri bottoni (Punti o Guida) il valore di pressed di Guanxy deve essere messo a false
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        //Prendo il singolo bottone
        final Button punti = (Button) findViewById(R.id.punti);

        //GESTIONE COLORE BOTTONI
        punti.setPressed(true);
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
                //mando in avvio l'activity
                PuntiActivity.this.startActivity(myIntent);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                guanxy.setSelected(false);
                guanxy.setPressed(false);


            };
        });



        //Creo una nuova dimensione per il bottone
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width/3, 100);
        //Gli do la regola, dato che quelle dell'xml sono sovrascritte
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        //Applico la nuova interfaccia al singolo bottone
        guanxy.setLayoutParams(layoutParams);
        //Creo una nuova interfaccia per il secondo bottone
        RelativeLayout.LayoutParams puntiParams = new RelativeLayout.LayoutParams(width/3, 100);
        puntiParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        puntiParams.addRule(RelativeLayout.RIGHT_OF, guanxy.getId());
        punti.setLayoutParams(puntiParams);

        //
        //Manca l'interfaccia per il terzo bottone
        //

    }
}
