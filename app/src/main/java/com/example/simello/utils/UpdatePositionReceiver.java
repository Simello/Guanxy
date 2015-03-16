package com.example.simello.utils;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.simello.classiServer.InsertUserInput;
import com.example.simello.classiServer.UpdatePositionInput;
import com.example.simello.guanxy.GuanxyActivity;
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
 * Created by Sunfury on 12/03/15.
 */
public class UpdatePositionReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String idUser = utils.numeroTelefonoCorrente(context);

        GPSManager gpsManager = new GPSManager(context);
        if(gpsManager.canGetLocation())
        {
            UpdatePositionInput updatePositionInput = new UpdatePositionInput(idUser, (long) gpsManager.getLatitude(), (long) gpsManager.getLongitude());
            connectAsyncTask connectAsyncTask = new connectAsyncTask("http://5.249.151.38:8080/guanxy/user/updatePosition");
            connectAsyncTask.execute(updatePositionInput);
        }
        else
        {
            Log.i("Aggiornamento","Non disponibile");
        }


    }



    private class connectAsyncTask extends AsyncTask<UpdatePositionInput, Void, String> {
        String url;
        connectAsyncTask(String urlPass){
            url = urlPass;
        }
        @Override
        protected String doInBackground(UpdatePositionInput... params) {

            UpdatePositionInput userInput = params[0];
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

                Log.i("POSIZIONE", s);
                response = httpclient.execute(request);
                Log.i("AGGIORNAMENTO POSIZIONE","fatto");


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

            Log.d("RitornoAGGIORNAMENTO",result);

            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }


}
