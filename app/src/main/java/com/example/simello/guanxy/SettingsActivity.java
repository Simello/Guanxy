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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.simello.controller.punteggi.Utente;

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

        //@Todo è da sistemare il toggle delle notifiche e della batteria
        ToggleButton notifiche = (ToggleButton) findViewById(R.id.toggleNotifiche);
        //QUI POSSO SETTARE IL METODO REALE DEL TOGGLE BUTTON DELLE NOTIFICHE
        notifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(SettingsActivity.this,"Cambiato",Toast.LENGTH_SHORT).show();
            }
        });
        //QUI INVECE SETTO IL TOGGLE DELLA BATTERIA
        ToggleButton batteria = (ToggleButton) findViewById(R.id.toggleBatteria);
        batteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this,"Cambiato la batteria ISI PISI",Toast.LENGTH_SHORT).show();

            }
        });

        Button username = (Button) findViewById(R.id.username);
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
                        user.setNome(m_Text);

                        SharedPreferences prefs = getSharedPreferences(
                                "com.example.app", Context.MODE_PRIVATE);
                        //Cambio lo username già salvato in anticipo in locale
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username", m_Text);
                        editor.commit();
                        Toast.makeText(SettingsActivity.this, "Username cambiato!",Toast.LENGTH_SHORT).show();
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
