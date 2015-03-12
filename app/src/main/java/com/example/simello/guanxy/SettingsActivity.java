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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.simello.controller.varie.User;
import com.example.simello.utils.utils;
import com.parse.ParsePush;
import com.parse.PushService;

/**
 * Created by Sunfury & Simello on 22/01/15.
 */
public class SettingsActivity extends PreferenceActivity
{
    private String m_Text = "";
    User user = User.getUser();





    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setContentView(R.layout.settings);

        SharedPreferences prefs = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        final SharedPreferences.Editor  editor = prefs.edit();

        final ToggleButton notifiche = (ToggleButton) findViewById(R.id.toggleNotifiche);
        //QUI POSSO SETTARE IL METODO REALE DEL TOGGLE BUTTON DELLE NOTIFICHE
        String statoNotifiche = prefs.getString("notifiche", "");
        if(statoNotifiche.compareTo("true") == 0)
            notifiche.setChecked(true);
        else
            notifiche.setChecked(false);



        //QUI INVECE SETTO IL TOGGLE DELLA BATTERIA
        final ToggleButton batteria = (ToggleButton) findViewById(R.id.toggleBatteria);
        String statoBatteria = prefs.getString("batteria","");
        if(statoBatteria.compareTo("true") == 0)
            batteria.setChecked(true);
        else
            batteria.setChecked(false);



        ImageButton username = (ImageButton) findViewById(R.id.username);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(getString(R.string.cambiaUsername));

                final EditText input = new EditText(SettingsActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                //Setta tasto OK
                builder.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();

                    }
                });
                //crea il tasto cancella
                builder.setNegativeButton(getString(R.string.Cancella), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });




        final ImageButton language = (ImageButton) findViewById(R.id.lingua);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
                startActivity(intent);

            }
        });


        //Se l'utente preme Salva, salva le impostazioni e torna alla schermata Guanxy
        final Button salva = (Button) findViewById(R.id.button_setting_salva);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //Setta il nome dell'user
                user.setNickname(m_Text);
                //Serve per salvare lo stato del toggle sia di batteria che notifica
                editor.putString("notifiche", ""+notifiche.isChecked());

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
     * Metodo che controlla la perdita del FOCUS della schermata attuale
     * @param hasFocus
     */
    //@todo Da sistemare l'onResume, deve chiudere per bene il Dialog
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        super.onWindowFocusChanged(hasFocus);
        utils.connect(hasFocus,this);

    }

}
