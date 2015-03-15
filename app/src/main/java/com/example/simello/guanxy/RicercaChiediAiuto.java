package com.example.simello.guanxy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.simello.classiServer.FindHelpRequestInput;
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
        findHelpRequestInput = new FindHelpRequestInput(id);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        progressTask = new ProgressTask("http://5.249.151.38:8080/guanxy/findHelpId");
        progressTask.execute(findHelpRequestInput);

        //TODO da aggiungere funzione bottone annulla ricerca
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        if(!progressTask.isCancelled())
            progressTask.cancel(true);


        Intent myIntent = new Intent(this, GuanxyActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }



private class ProgressTask extends AsyncTask<FindHelpRequestInput,Void,String> {
    String url;
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

            if(json.has("userReceive")) {
                JSONObject receive = json.getJSONObject("userReceive");
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

        }

    }
}
}
