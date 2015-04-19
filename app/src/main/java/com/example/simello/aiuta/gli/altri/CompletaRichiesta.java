package com.example.simello.aiuta.gli.altri;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.simello.classiServer.CompleteHelpRequestInput;
import com.example.simello.classiServer.TakingCareHelpReuqestInput;
import com.example.simello.controller.varie.Richiesta;
import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.DatiProvaAccordion;
import com.example.simello.guanxy.GuanxyActivity;
import com.example.simello.guanxy.R;
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

/**
 * Created by Sunfury on 11/04/15.
 */
public class CompletaRichiesta extends Activity {

    private connectAsyncTaskFine connectAsyncTaskFine;
    private CompleteHelpRequestInput completeHelpRequestInput;
    private RatingBar ratingBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.completa_richiesta);

        ratingBar = (RatingBar) findViewById(R.id.ratingCompleta);
        Button invia = (Button) findViewById(R.id.buttonInviaCompletaRichiesta);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Richiesta richiesta = Richiesta.getRichiesta();
                int point = Math.round(ratingBar.getRating() * 2);
                completeHelpRequestInput = new CompleteHelpRequestInput(richiesta.getIdRichiesta(), User.getUser().getIdUser(), point);
                connectAsyncTaskFine = new connectAsyncTaskFine("http://5.249.151.38:8080/guanxy/completeRequest");
                connectAsyncTaskFine.execute(completeHelpRequestInput);


            }
        });


    }

    //CREO L'ASKYNCTASK PER LA CONNESSIONE
    private class connectAsyncTaskFine extends AsyncTask<CompleteHelpRequestInput, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        Intent i;

        connectAsyncTaskFine(String urlPass) {
            url = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(CompletaRichiesta.this);
            progressDialog.setMessage("Attendi...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(CompleteHelpRequestInput... params) {

            CompleteHelpRequestInput finish = params[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(finish);
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
                Log.d("RitornoAccetta", result);

                i = new Intent(CompletaRichiesta.this, GuanxyActivity.class);

            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }


            return result;

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing() && result.compareTo("error") != 0) {
                progressDialog.dismiss();
                startActivity(i);
            } else {
                connectAsyncTaskFine.cancel(true);
                connectAsyncTaskFine = new connectAsyncTaskFine("http://5.249.151.38:8080/guanxy/completeRequest");
                connectAsyncTaskFine.execute(completeHelpRequestInput);
            }


        }
    }
}
