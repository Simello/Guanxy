package com.example.simello.guanxy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simello.classiServer.InsertHelpRequestInput;
import com.example.simello.classiServer.InsertUserInput;
import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.utils;
import com.fasterxml.jackson.databind.ObjectMapper;

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

/**
 * Created by Sunfury on 20/01/15.
 */
public class ChiediAiuto extends ActionBarActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiedi_aiuto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final Button invia = (Button) findViewById(R.id.buttonInviaRichiesta);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextRichiesta = (EditText) findViewById(R.id.richiesta);

                //Prendo l'username
                String richiesta = editTextRichiesta.getText().toString();
                if (richiesta.matches("")) {
                    Toast.makeText(ChiediAiuto.this, "Non hai inserito una richiesta corretta!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // /newHelpRequest
                InsertHelpRequestInput helpRequestInput = new InsertHelpRequestInput(User.getUser().getIdUser(), GPSManager.newInstance(ChiediAiuto.this).getLatitude(), GPSManager.newInstance(ChiediAiuto.this).getLongitude(), richiesta);

                connectAsyncTask connectAsyncTask = new connectAsyncTask("http://5.249.151.38:8080/guanxy/newHelpRequest", ChiediAiuto.this);
                connectAsyncTask.execute(helpRequestInput);

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



    private class connectAsyncTask extends AsyncTask<InsertHelpRequestInput, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        Context cnt;
        connectAsyncTask(String urlPass, Context cnt){
            url = urlPass;
            this.cnt = cnt;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChiediAiuto.this);
            progressDialog.setMessage("Sto Inviando la richiesta!");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(InsertHelpRequestInput... params) {
            InsertHelpRequestInput userInput = params[0];
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
                response = httpclient.execute(request);
                Log.i("InvioRichiesta","" + s);

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

                JSONObject json = new JSONObject(result);
                JSONObject help = json.getJSONObject("help");
                String id = help.getString("id");
                result = id;
                Log.i("RitornoID",""+id);
            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }
            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if(result.compareTo("error") == 0)
            {

            }
            else
            {
                Intent i = new Intent(ChiediAiuto.this, RicercaChiediAiuto.class);
                i.putExtra("idRichiesta",result);
                //Ci vanno flag??
                startActivity(i);
                overridePendingTransition(0, 0);
            }

        }
    }
}
