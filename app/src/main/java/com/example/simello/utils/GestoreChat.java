package com.example.simello.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.simello.classiServer.NewMessageInput;
import com.example.simello.controller.varie.User;
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
 * Created by simello on 07/04/15.
 */
public class GestoreChat
{
    String id_usr1;
    String id_richiesta;
    public GestoreChat(String id_richiesta)
    {
        this.id_usr1 = User.getUser().getIdUser(); //prende l'user dell'utente del cellulare
        this.id_richiesta=id_richiesta;
    }

    public void nuovoMessaggio(String testoMessaggio)
    {
        NewMessageInput nmi = new NewMessageInput(id_richiesta, id_usr1, testoMessaggio);
        connectAsyncTaskNewMessage connectAsyncTaskNewMessage = new connectAsyncTaskNewMessage("http://5.249.151.38:8080/guanxy/newChat");
        connectAsyncTaskNewMessage.execute(nmi);

    }


    private class connectAsyncTaskNewMessage extends AsyncTask<NewMessageInput, Void, String> {

        String url;
        connectAsyncTaskNewMessage(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(NewMessageInput... params) {

            NewMessageInput userAccepter = params[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(userAccepter);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


                Log.i("AccettaRichiesta", s);
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
                Log.d("RitornoAccetta", result);


            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }


            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);




        }
    }



}


