package com.example.simello.guanxy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.simello.classiServer.FindUserInput;
import com.example.simello.controller.varie.User;
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
 * Created by Sunfury & Simello on 17/01/15.
 */
public class PuntiActivity extends ActionBarActivity
{
    protected int point;
    TextView vistaPunti;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punteggi_fragment);

        String idUser = utils.numeroTelefonoCorrente(this);
        //TODO cambiare il numero di telefono!
        FindUserInput findUserInput = new FindUserInput(idUser);
        ProgressTask progressTask = new ProgressTask("http://5.249.151.38:8080/guanxy/user/getUser");
        progressTask.execute(findUserInput);


        User user = User.getUser();
        //Stampa a video l'username dell'utente
        TextView username = (TextView) findViewById(R.id.user);
        username.setText(user.getNickname());
        //Stampa a video i punti dell utente
        vistaPunti = (TextView) findViewById(R.id.puntiSchermataPunti);
        vistaPunti.setText(""+user.getPoint());



        //REPARTO BOTTONI
        //Prendo il bottone e setto a true x lasciare il colore blue
        //DA RICORDARE
        //Quando si premeranno gli altri bottoni (Punti o Guida) il valore di pressed di Guanxy deve essere messo a false
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        //Prendo il singolo bottone
        final Button punti = (Button) findViewById(R.id.punti);

        final Button guida = (Button) findViewById(R.id.guida);

        //GESTIONE COLORE BOTTONI
        punti.setPressed(true);
        punti.setSelected(true);
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
                //gli dico che se preme BACK FISICO, allora chiude app.
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                //mando in avvio l'activity
                PuntiActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);


            };
        });

        guida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                punti.setSelected(false);
                punti.setPressed(false);

                Intent myIntent = new Intent(PuntiActivity.this, GuidaActivity.class);

                PuntiActivity.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

            };
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        findViewById(R.id.punti).setPressed(false);
        findViewById(R.id.punti).setPressed(false);


        Intent myIntent = new Intent(this, GuanxyActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
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


    private class ProgressTask extends AsyncTask<FindUserInput,Void,String> {
        String url;
        private ProgressDialog progressDialog;


        ProgressTask(String url) {
            this.url = url;
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(PuntiActivity.this);
            progressDialog.setMessage("Aggiorno...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(FindUserInput... arg0) {
            FindUserInput findInput = arg0[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(findInput);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                response = httpclient.execute(request);
            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }

            // response code
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {

                    result = result + line;
                }
                JSONObject json = new JSONObject(result);
                JSONObject user = json.getJSONObject("user");
                point = user.getInt("point");
                User.getUser().setPoint(point);
                vistaPunti.setText(""+User.getUser().getPoint());


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
        }

    }
}
