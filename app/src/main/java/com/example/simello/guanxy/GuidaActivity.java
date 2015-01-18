package com.example.simello.guanxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

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

        int width = getWindowManager().getDefaultDisplay().getWidth();
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        final Button punti = (Button) findViewById(R.id.punti);
        final Button guida = (Button) findViewById(R.id.guida);

        guida.setPressed(true);

        guanxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);


                guida.setPressed(false);
                guida.setSelected(false);
                Intent myIntent = new Intent(GuidaActivity.this, GuanxyActivity.class);

                GuidaActivity.this.startActivity(myIntent);

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

            };
        });






    }
}
