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
import android.widget.TextView;
import android.widget.Toast;

import com.example.simello.classiServer.InsertUserInput;
import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.GuanxyActivity;
import com.example.simello.guanxy.R;
import com.example.simello.utils.AsyncConnection;
import com.example.simello.utils.GPSManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sunfury on 03/03/15.
 */
public class RegistrazioneUsername extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_username);
        TextView textView = (TextView) findViewById(R.id.NewUsernameText);
        textView.setTextColor(getResources().getColor(R.color.white));


        Button inizio = (Button) findViewById(R.id.buttonInizia);
        inizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText usernameEditText = (EditText) findViewById(R.id.NewUsername);
                //Prendo l'username
                String sUsername = usernameEditText.getText().toString();
                if (sUsername.matches("")) {
                    Toast.makeText(RegistrazioneUsername.this, "Non hai inserito un nome utente corretto", Toast.LENGTH_SHORT).show();
                    return;
                }

                GPSManager gpsManager = new GPSManager(RegistrazioneUsername.this);
                //Prendo la posizione
                Position position = new Position((float)gpsManager.getLatitude(),(float) gpsManager.getLongitude());
                //Creo l'oggetto
                InsertUserInput userInput = new InsertUserInput("3208814625",sUsername,(long)gpsManager.getLongitude(),(long)gpsManager.getLatitude());

                //Creo un oggettto di tipo connectAsyncTask (con Dialog rotella) e gli passo l url dello script
                connectAsyncTask connection = new connectAsyncTask("http://5.249.151.38:8080/guanxy/user");
                //Lo eseguo passandogli come parametro l oggetto creato!
                connection.execute(userInput);


                //Questo è da spostare nel doOnPostExecute con i ritorni decenti... cazzo è sto OK? e sto fail? BICC VOGLIAMO I PUNTI
                User.getIstance(sUsername,RegistrazioneUsername.this,"3208814625",0,position);
                User u = User.getUser();
                u.setNickname(sUsername);



                Intent i = new Intent(RegistrazioneUsername.this, GuanxyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("PIN","No");
                startActivity(i);

                //todo esegui script registrazione

            }
        });




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(this, GuanxyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
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
            progressDialog.setMessage("Sto Registrando!");
            progressDialog.setIndeterminate(true);
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

            Log.d("Ritorno",result);

            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();

        }
    }
}