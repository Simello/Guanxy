package com.example.simello.registrazione;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simello.classiServer.AuthenticateUserInput;
import com.example.simello.classiServer.InsertUserInput;
import com.example.simello.guanxy.GuanxyActivity;
import com.example.simello.guanxy.R;
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
 * Created by Sunfury on 22/03/15.
 */
public class RegistrazionePin extends Activity
{
    private String userName = "";
    private String pin = "";
    private String numeroTelefono = "";
    private String punti = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_pin);
        //Prendo i valori precedenti
        Intent intent = getIntent();
        userName = intent.getStringExtra("nickname");
        numeroTelefono = intent.getStringExtra("numeroTelefono");


        //Setto il bottone
        Button invia = (Button) findViewById(R.id.buttonInviaPin);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText editPin = (EditText) findViewById(R.id.pin);
                pin = editPin.getText().toString().trim();

                if (pin.matches("")) {
                    Toast.makeText(RegistrazionePin.this, "Errore", Toast.LENGTH_SHORT).show();
                    return;
                }

                AuthenticateUserInput authenticateUserInput = new AuthenticateUserInput(numeroTelefono, pin);
                connectAsyncTask connectAsyncTask = new connectAsyncTask("http://5.249.151.38:8080/guanxy/user/authenticatedUser");
                connectAsyncTask.execute(authenticateUserInput);




            }
        });








    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        super.onWindowFocusChanged(hasFocus);
        utils.connectNoUser(hasFocus, this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(this, RegistrazioneUsername.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
        return super.onKeyDown(keyCode, event);
    }


    private class connectAsyncTask extends AsyncTask<AuthenticateUserInput, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        connectAsyncTask(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrazionePin.this);
            progressDialog.setMessage("Sto Inviando!");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(AuthenticateUserInput... params) {

            AuthenticateUserInput userInput = params[0];
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

                Log.i("RitornoPin",result);


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
            boolean flag = false;
            if( result.compareTo("error") == 0)
            {
                //todo qui c'è da gestire il caso in cui non vada a buon fine la connessione x la registrazion

            }
            else {
                try {
                    JSONObject json = new JSONObject(result);
                    if(json.getString("message").compareTo("Utente autenticato") == 0)
                        flag = true;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                //se l'utente si è autenticato correttamente passa avanti
                if(flag) {
                    Intent i = new Intent(RegistrazionePin.this, GuanxyActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("PIN", pin);
                    i.putExtra("numeroTelefono", numeroTelefono);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                }
                //todo altrimenti???
            }

        }
    }

}
