package com.example.simello.guanxy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.simello.aiuta.gli.altri.TabAiutaGliAltri;
import com.example.simello.classiServer.CancelHelpRequestInput;
import com.example.simello.classiServer.FindHelpRequestInput;
import com.example.simello.controller.varie.Richiesta;
import com.example.simello.controller.varie.User;
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
import java.math.BigInteger;

/**
 * Created by Sunfury on 13/03/15.
 */
public class RicercaChiediAiuto extends Activity
{
    private ProgressBar bar;
    private BigInteger id;
    protected FindHelpRequestInput findHelpRequestInput;
    protected ProgressTask progressTask;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ricerca_chiedi_aiuto);

        String idString = getIntent().getStringExtra("idRichiesta");
        id = new BigInteger(idString);
        findHelpRequestInput = new FindHelpRequestInput(id, User.getUser().getIdUser());

        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        progressTask = new ProgressTask("http://5.249.151.38:8080/guanxy/findHelpId");
        progressTask.execute(findHelpRequestInput);

        final Button annullaRichiesta = (Button) findViewById(R.id.annullaRichiesta);
        annullaRichiesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!progressTask.isCancelled())
                    progressTask.cancel(true);
                CancelHelpRequestInput cancelHelpRequestInput = new CancelHelpRequestInput(id, User.getUser().getIdUser());
                ProgressTaskCancella progressTaskCancella = new ProgressTaskCancella("http://5.249.151.38:8080/guanxy/cancelRequest");
                progressTaskCancella.execute(cancelHelpRequestInput);

            }
        });

        //TODO da aggiungere funzione bottone annulla ricerca
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        if(!progressTask.isCancelled())
            progressTask.cancel(true);
        CancelHelpRequestInput cancelHelpRequestInput = new CancelHelpRequestInput(id, User.getUser().getIdUser());
        ProgressTaskCancella progressTaskCancella = new ProgressTaskCancella("http://5.249.151.38:8080/guanxy/cancelRequest");
        progressTaskCancella.execute(cancelHelpRequestInput);


        Intent myIntent = new Intent(this, GuanxyActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }



private class ProgressTask extends AsyncTask<FindHelpRequestInput,Void,String> {
    String url;
    Intent i;
    ProgressTask(String url)
    {
        this.url = url;
    }
    @Override
    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(FindHelpRequestInput... arg0)
    {
        FindHelpRequestInput findHelpRequestInput = arg0[0];
        HttpClient httpclient;
        HttpPost request;
        HttpResponse response = null;
        String result = "";

        try {
            httpclient = new DefaultHttpClient();
            request = new HttpPost(url);

            ObjectMapper objectWriter = new ObjectMapper();

            String s = objectWriter.writeValueAsString(findHelpRequestInput);
            StringEntity se = new StringEntity(s);
            request.setEntity(se);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


            Log.i("OggIdRichiesta", s);
            response = httpclient.execute(request);
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

                result = result + line;
            }
            JSONObject json = new JSONObject(result);
            JSONObject help = json.getJSONObject("help");
            Log.i("Test",help.toString());


            if(!help.isNull("userReceive")) {
                JSONObject receive = help.getJSONObject("userReceive");
                i = new Intent(RicercaChiediAiuto.this, TabAiutaGliAltri.class);

                Richiesta.newRichiesta(receive.getString("nickname"), BigInteger.valueOf(receive.getInt("id")),receive.getDouble("latitude"),receive.getDouble("longitude"));
/*
                i.putExtra("idUser",receive.getString("nickname"));
                i.putExtra("idRichiesta",receive.getInt("id"));
                i.putExtra("Lat",receive.getDouble("latitude"));
                Log.i("Lat RCA" ,"" + receive.getDouble("latitude") );
                i.putExtra("Lon",receive.getDouble("longitude"));
                Log.i("Lon RCA" , "" +receive.getDouble("longitude"));
                */
            }
            else
            {
                result = "noUser";
            }


        }
        catch (Exception e) {
            // Code to handle exception
            result = "error";
        }

        Log.d("Ritorno",result);

        try {
            Thread.currentThread();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        if(result.compareTo("noUser") == 0)
        {
            progressTask.cancel(true);
            progressTask = new ProgressTask("http://5.249.151.38:8080/guanxy/findHelpId");
            progressTask.execute(findHelpRequestInput);

        }
        else
        {
            bar.setVisibility(View.GONE);
            startActivity(i);
            //... Non posso continuare lelling, ho bisogno

        }

    }
}


    private class ProgressTaskCancella extends AsyncTask<CancelHelpRequestInput,Void,String> {
        String url;

        ProgressTaskCancella(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(CancelHelpRequestInput... arg0) {
            CancelHelpRequestInput cancelHelpRequestInput = arg0[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(cancelHelpRequestInput);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


                Log.i("OggIdCancella", s);
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



            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }

            Log.d("Ritorno", result);



            return result;

        }

        @Override
        protected void onPostExecute(String result)
        {
          Intent i = new Intent(RicercaChiediAiuto.this, GuanxyActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            overridePendingTransition(0, 0);
            startActivity(i);

            }


    }
}
