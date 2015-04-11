package com.example.simello.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.simello.classiServer.Message;
import com.example.simello.classiServer.MessageByTimeInput;
import com.example.simello.classiServer.MessageByTimeOutput;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by simello on 07/04/15.
 */
public class GestoreChat
{
    String id_usr1;
    BigInteger idRichiesta;
    TreeMap<Double, String> messages;

    public GestoreChat(BigInteger id_richiesta)
    {
        this.id_usr1 = User.getUser().getIdUser(); //prende l'user dell'utente del cellulare
        this.idRichiesta=id_richiesta;
        this.messages = new TreeMap<Double, String>();
    }

    public boolean controlla()
    {
        Date time = new Date();
        time.setTime(time.getTime() - 10000);
        MessageByTimeInput messageByTimeInput = new MessageByTimeInput(idRichiesta, id_usr1, time);
        connectAsyncTaskCheck connectAsyncTaskCheck = new connectAsyncTaskCheck("http://5.249.151.38:8080/guanxy/messageFromTime");
        connectAsyncTaskCheck.execute(messageByTimeInput);

        if(messages.size() > 0)
        {
            return true;
        }
        return false;

    }

    public TreeMap<Double,String> getMessages()
    {
        return messages;
    }


    public void nuovoMessaggio(String testoMessaggio)
    {
        Log.i("IdRichiesta", ""+idRichiesta.toString());
        Log.i("IdUser", ""+id_usr1);
        Log.i("Text", ""+testoMessaggio);
        NewMessageInput nmi = new NewMessageInput(idRichiesta.toString() , id_usr1, testoMessaggio);
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

            NewMessageInput messaggio = params[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);
                ObjectMapper objectWriter = new ObjectMapper();
                String s = objectWriter.writeValueAsString(messaggio);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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

                    result = result + line ;
                }
            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }
            Log.i("MessaggioInvia", result);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    private class connectAsyncTaskCheck extends AsyncTask<MessageByTimeInput, Void, String> {

        String url;
        connectAsyncTaskCheck(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(MessageByTimeInput... params) {

            MessageByTimeInput check = params[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);
                ObjectMapper objectWriter = new ObjectMapper();
                String s = objectWriter.writeValueAsString(check);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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

                    result = result + line ;
                }

                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = jsonObject.getJSONArray("messages");
                if(array.length() > 0)
                {
                    for (int i = 0; i < array.length(); i++) {
                        String textMsg = array.getJSONObject(i).getString("messageTxt");
                        Double id =  array.getJSONObject(i).getDouble("id");
                        messages.put(id, textMsg);

                    }
                }


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


