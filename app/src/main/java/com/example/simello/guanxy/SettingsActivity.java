package com.example.simello.guanxy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.simello.controller.punteggi.Utente;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.utils;

/**
 * Created by Sunfury & Simello on 22/01/15.
 */
public class SettingsActivity extends PreferenceActivity
{
    private String m_Text = "";
    Utente user = Utente.getUser();




    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setContentView(R.layout.settings);

        SharedPreferences prefs = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        final SharedPreferences.Editor  editor = prefs.edit();

        //@Todo è da sistemare il toggle delle notifiche e della batteria
        final ToggleButton notifiche = (ToggleButton) findViewById(R.id.toggleNotifiche);
        //QUI POSSO SETTARE IL METODO REALE DEL TOGGLE BUTTON DELLE NOTIFICHE
        String statoNotifiche = prefs.getString("notifiche","");
        if(statoNotifiche.compareTo("true") == 0)
            notifiche.setChecked(true);
        else
            notifiche.setChecked(false);

        notifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(SettingsActivity.this,"Cambiato",Toast.LENGTH_SHORT).show();

            }
        });



        //QUI INVECE SETTO IL TOGGLE DELLA BATTERIA
        final ToggleButton batteria = (ToggleButton) findViewById(R.id.toggleBatteria);
        String statoBatteria = prefs.getString("batteria","");
        if(statoBatteria.compareTo("true") == 0)
            batteria.setChecked(true);
        else
            batteria.setChecked(false);

        batteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this,"Cambiato la batteria ISI PISI",Toast.LENGTH_SHORT).show();

            }
        });


//@Todo aggiungere l'aggiornamento al db quando viene cambiato l'username!
        ImageButton username = (ImageButton) findViewById(R.id.username);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Cambia Username");

                final EditText input = new EditText(SettingsActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                //Setta tasto OK
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();

                    }
                });
                //crea il tasto cancella
                builder.setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });

        //Se l'utente preme Salva, salva le impostazioni e torna alla schermata Guanxy
        final Button salva = (Button) findViewById(R.id.button_setting_salva);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //Setta il nome dell'user
                user.setNome(m_Text);
                //Serve per salvare lo stato del toggle sia di batteria che notifica
                editor.putString("notifiche",""+notifiche.isChecked());
                editor.putString("batteria", ""+batteria.isChecked());


                //Appica le modifiche e le salva
                editor.apply();

                Toast.makeText(SettingsActivity.this, "Salvato!",Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(SettingsActivity.this, GuanxyActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });
        //Se l'utente preme cancella, torna indietro senza cambiare nulla
        final Button cancella = (Button) findViewById(R.id.button_setting_cancella);
        cancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(SettingsActivity.this, GuanxyActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

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
