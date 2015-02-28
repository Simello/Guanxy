package com.example.simello.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Simello & Sunfury on 27/01/15.
 */
public class AsyncConnection extends AsyncTask<HashMap<String,Object>, Void, String> {

    private Context context;


    public AsyncConnection(Context cxt) {
        context = cxt;
    }


    protected String doInBackground(HashMap<String,Object>... params) {
        HttpClient httpclient;
        HttpPost request;
        HttpResponse response = null;
        String result = "";
        // TextView to display result
        HashMap<String,Object> invio = params[0];
        // Try to connect using Apache HttpClient Library
        try {
            httpclient = new DefaultHttpClient();
            request = new HttpPost((String)invio.get("url")); //URL

            //Preparo la mappa
            ObjectMapper objectWriter = new ObjectMapper();

            String s = objectWriter.writeValueAsString((Object)invio.get("User"));
            StringEntity se = new StringEntity(s);
            request.setEntity(se);

            Log.i("OBJECT",s);
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

        try
        {
            Log.i("Ritorno",result);
            JSONObject jObj = new JSONObject(result);
            Log.e("Ritorno",jObj.toString());

        }
        catch (JSONException e)
        {
            Log.e("JSONException", "Error: " + e.toString());
        }
        return result;
    }


    protected void onPostExecute(String result)

    {
        Log.e("Risultato",result);
    }

}


/*

//Come scompattare il risultato dal db
protected void onPostExecute(String result)

    {
        try {
            JSONArray jArray = new JSONArray(result);
            for(int i = 0; i < jArray.length(); i++)
            {
                JSONObject jObj = jArray.getJSONObject(i);
                String n = jObj.getString("nickname");
                Log.e("Result",n);
            }
        }
        catch(JSONException e)
        {
            Log.e("JSONException","Error: " + e.toString());
        }
    }
 */