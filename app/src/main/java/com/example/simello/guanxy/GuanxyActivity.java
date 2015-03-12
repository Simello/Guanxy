package com.example.simello.guanxy;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.registrazione.RegistrazioneTabActivity;
import com.example.simello.utils.AsyncConnection;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.UpdatePositionReceiver;
import com.example.simello.utils.utils;
import com.parse.ParseInstallation;
import com.parse.PushService;

import java.util.ArrayList;
import java.util.List;







public class GuanxyActivity extends ActionBarActivity
{

    private String mPhoneNumber;
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Se è true, allora esce dall'applicazione!
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
        TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();
        if(mPhoneNumber == null)
        {
        //todo da annullare poiche non ha il numero di telefono!

        }


        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        //Serve per evitare di rieseguire la registrazione!!!
        String pin = getIntent().getStringExtra("PIN");
        if(pin != null)
        {
            SharedPreferences.Editor  editor = prefs.edit();
            editor.putString("PIN","No");
            editor.apply();
        }


        //@Todo
        //Registrazione per il primo login o exit
        String code = prefs.getString("PIN","PIN");
        if (code.compareTo("PIN") == 0)
        {

            Intent i = new Intent(this, RegistrazioneTabActivity.class);
            startActivity(i);

/*
                HashMap<String,Object> invio = new HashMap<String, Object>();
                invio.put("url","http://192.168.1.186:8080/guanxy-web/newuser");

                invio.put("User",user);

                AsyncConnection cnt = new AsyncConnection(this);

                cnt.execute(invio);
                */

        }
        else
        {
            String username = prefs.getString("nickname","");
            //todo da aggiungere il getPoints e il getNumeroTelefono
            GPSManager gpsManager = new GPSManager(this);

            Position position = new Position((float)gpsManager.getLatitude(),(float) gpsManager.getLongitude());

            User.getIstance(username,this,"3208814625",0,position);
        }


//todo controllare connessione -> ma serve? e a cosa?


        ParseInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, GuanxyActivity.class);

        setContentView(R.layout.fragment_main);


        Intent alarmIntent = new Intent(this, UpdatePositionReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 10000; //10 secs

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);




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

        ImageButton aiutaGliAltri = (ImageButton)findViewById(R.id.buttonTextPremi);
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
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//Serve per sistemare i bottoni quando viene premuto il tasto "back" fisico
    //Per evitare problemi, crea una nuova activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //todo da lasciare per testare la registrazione
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PIN","PIN");
        editor.apply();

        overridePendingTransition(0, 0);
     //Se sono già a Guanxy, esco
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
