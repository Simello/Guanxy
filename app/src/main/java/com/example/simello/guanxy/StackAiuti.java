package com.example.simello.guanxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by simello on 12/03/15.
 */

    public class StackAiuti extends Activity {


    ListView listview;
    int flagSimelloFreccia = 0;
    TextView text1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_aiuta_altri);
        listview = (ListView) findViewById(R.id.listViewAga);
        listview.setAdapter(new AdapterAga(this, new String[]{"data1",
                "data2"}));




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

                Intent myIntent = new Intent(StackAiuti.this, GuanxyActivity.class);

                StackAiuti.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }

            ;
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                guanxy.setSelected(false);
                guanxy.setPressed(false);


                Intent myIntent = new Intent(StackAiuti.this, PuntiActivity.class);
                StackAiuti.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }

            ;
        });

        guida.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.setSelected(true);

                guanxy.setSelected(false);
                guanxy.setPressed(false);

                Intent myIntent = new Intent(StackAiuti.this, GuidaActivity.class);

                StackAiuti.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });

    }










}