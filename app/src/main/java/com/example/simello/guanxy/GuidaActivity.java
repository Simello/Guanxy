package com.example.simello.guanxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.simello.utils.GPSManager;
import com.example.simello.utils.utils;

/**
 * Created by Sunfury & Simello on 17/01/15.
 */
public class GuidaActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guida_fragment);

        final Button guanxy = (Button) findViewById(R.id.guanxy);
        final Button punti = (Button) findViewById(R.id.punti);
        final Button guida = (Button) findViewById(R.id.guida);

        guida.setPressed(true);
        guida.setSelected(true);

        guanxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);


                guida.setPressed(false);
                guida.setSelected(false);
                Intent myIntent = new Intent(GuidaActivity.this, GuanxyActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                GuidaActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);


            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);


                guida.setSelected(false);
                guida.setPressed(false);
                Intent myIntent = new Intent(GuidaActivity.this, PuntiActivity.class);

                GuidaActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);


            };
        });

        guida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);



            };
        });






    }


    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        findViewById(R.id.guida).setPressed(false);
        findViewById(R.id.guida).setPressed(false);

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
