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

import com.example.simello.classiServer.InsertUserInput;
import com.example.simello.controller.varie.Position;
import com.example.simello.guanxy.R;
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
 * Created by Sunfury on 03/03/15.
 */
public class RegistrazioneUsername extends Activity
{
   // protected User u;
    private String sUsername = "";
    private String numeroTelefono = "";
    private String punti = "";
    private String prefix = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_username);


        Button inizio = (Button) findViewById(R.id.buttonInizia);
        inizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                EditText usernameEditText = (EditText) findViewById(R.id.NewUsername);
                //Prendo l'username
                sUsername = usernameEditText.getText().toString();
                if (sUsername.matches("")) {
                    Toast.makeText(RegistrazioneUsername.this, "Non hai inserito un nome utente corretto", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText numeroTelefonoEdit = (EditText) findViewById(R.id.numeroTelefono);
                //Prendo il numero di telefono
                numeroTelefono = numeroTelefonoEdit.getText().toString();
                if (numeroTelefono.matches("")) {
                    Toast.makeText(RegistrazioneUsername.this, "Non hai inserito un numero corretto", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText prefixEdit = (EditText) findViewById(R.id.prefix);

                prefix = prefixEdit.getText().toString();
                if (prefix.matches("")) {
                    Toast.makeText(RegistrazioneUsername.this, "Non hai inserito un preefisso corretto", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(prefix.contains("+"))
                {
                    prefix = prefix.replace("+","");
                }



                GPSManager gpsManager = new GPSManager(RegistrazioneUsername.this);
                //Creo l'oggetto
                InsertUserInput userInput = new InsertUserInput(numeroTelefono, prefix ,sUsername,gpsManager.getLatitude(),gpsManager.getLongitude());

                //Creo un oggettto di tipo connectAsyncTask (con Dialog rotella) e gli passo l url dello script
                connectAsyncTask connection = new connectAsyncTask("http://5.249.151.38:8080/guanxy/user");
                //Lo eseguo passandogli come parametro l oggetto creato!

                connection.execute(userInput);
/*
                User.getIstance(sUsername, RegistrazioneUsername.this, utils.numeroTelefonoCorrente(RegistrazioneUsername.this), 0, position);
                u = User.getUser();
                u.setNickname(sUsername);
                */
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
            Intent intent = new Intent(this, RegistrazioneTabActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
        return super.onKeyDown(keyCode, event);
    }




    private class connectAsyncTask extends AsyncTask<InsertUserInput, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        connectAsyncTask(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrazioneUsername.this);
            progressDialog.setMessage("Sto Inviando!");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(InsertUserInput... params) {

            InsertUserInput userInput = params[0];
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

            Log.d("RitornoUsername",result);

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
                    //Prendo i punti dell'utente
                    punti = Integer.toString(user.getInt("point"));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



                Intent i = new Intent(RegistrazioneUsername.this, RegistrazionePin.class);
                //Passo tutti i valori alla parte successiva, se anche quella va a buon fine, allora registro
                i.putExtra("nickname",sUsername);
                i.putExtra("numeroTelefono",numeroTelefono);
                i.putExtra("punti",punti);
                startActivity(i);
                overridePendingTransition(0, 0);
            }

        }
    }
}
