package com.example.simello.guanxy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.simello.classiServer.AuthenticateUserInput;
import com.example.simello.classiServer.FindUserInput;
import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.registrazione.RegistrazioneTabActivity;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.UpdatePositionReceiver;
import com.example.simello.utils.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parse.ParseInstallation;
import com.parse.PushService;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GuanxyActivity extends ActionBarActivity
{

    private int interval;
    private String mPhoneNumber;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private String batteria = "false";
    private GPSManager gpsManager;
    private static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se è true, allora esce dall'applicazione!
        if (getIntent().getBooleanExtra("EXIT", false)) {
            //Cancella l'activity e chiude l'app -> Quando si riapre, riparte l'application
            //Cosi controlla che l'utente sia registrato o no
           finish();
           System.exit(0);
        }


        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        gpsManager = new GPSManager(this);


        //Serve per evitare di rieseguire la registrazione!!!
        String pin = getIntent().getStringExtra("PIN");
        if(pin != null)
        {
            SharedPreferences.Editor  editor = prefs.edit();
            //Ci salvo il numero di telefono, cosi se l utente cambia scheda, deve effettuare una nuova registrazione
            //Sorry bro!
            editor.putString("numeroTelefono",getIntent().getStringExtra("numeroTelefono"));
            editor.putString("PIN",pin);
            editor.apply();
        }


        //Prendo il numero di telefono
        mPhoneNumber = utils.numeroTelefonoCorrente(this);
        String code = prefs.getString("PIN", "PIN");


        // code.compareTo("PIN") == 0 || code.compareTo(""+mPhoneNumber) != 0  <---- Questp sarà l'if finale
        if (code.compareTo("PIN") == 0 || mPhoneNumber.compareTo("") == 0) {
            Intent i = new Intent(this, RegistrazioneTabActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else
        {
            if(u == null) {
                FindUserInput findUserInput = new FindUserInput(utils.numeroTelefonoCorrente(this));
                connectAsyncTask connectAsyncTask = new connectAsyncTask("http://5.249.151.38:8080/guanxy/user/getUser");
                connectAsyncTask.execute(findUserInput);
            }
            else
                Log.i("User","Non nullo");
        }


        ParseInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, GuanxyActivity.class);

        setContentView(R.layout.fragment_main);

        //Creazione fase autoaggiornamentoPosizione

        batteria = prefs.getString("batteria","false");
        addAlarm(batteria);

        //Fine creazione fase autoaggiornamentoPosizione


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

                // chiamo l'accordion dinamico
                Intent myIntent = new Intent(GuanxyActivity.this, HelloAccordion_JAVA.class);
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

    /**
     * Setta il timer per autoAggiornare la posizione
     * @param batteria valore per controllo Settings
     */
    private void addAlarm(String batteria)
    {
        boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                new Intent(this, UpdatePositionReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if(!alarmUp) {
            Intent alarmIntent = new Intent(this, UpdatePositionReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            if (batteria.compareTo("false") == 0)
                interval = 60000 * 15 ; //15 minuti
            else
                interval = 60000 * 30; //30 minuti

            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        }


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
/*
        //todo da lasciare per testare la registrazione
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PIN","PIN");
        editor.apply();
        */

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

    private class connectAsyncTask extends AsyncTask<FindUserInput, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        connectAsyncTask(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(GuanxyActivity.this);
            progressDialog.setMessage("Sto cercando l'utente!");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(FindUserInput... params) {

            FindUserInput userInput = params[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(userInput);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                Log.i("OBJECT", s);
                response = httpclient.execute(request);

                Log.i("Invio","fatto");


            }

            catch (Exception e) {
                // Code to handle exception
                result = "error";
            }

            // response code
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {

                    result = result + line ;
                }


            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }

            Log.d("RitornoGuanxy",result);

            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if( result.compareTo("error") == 0)
            {
                //todo qui c'è da gestire il caso in cui non vada a buon fine la connessione x la registrazion

            }
            else {
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject user = json.getJSONObject("user");
                    Position position = new Position(gpsManager.getLatitude(),gpsManager.getLongitude());
                    u = User.getIstance(user.getString("nickname"), GuanxyActivity.this, mPhoneNumber, user.getInt("point"), position);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
    }




}
