package com.example.simello.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.simello.guanxy.GuanxyActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Simello & Sunfury on 27/01/15.
 */
public class AsyncConnection extends AsyncTask<String, Void, String> {

    private Context context;


    public AsyncConnection(Context cxt) {
        context = cxt;
    }

    protected String doInBackground(String... message) {
        HttpClient httpclient;
        HttpGet request;
        HttpResponse response = null;
        String result = "error";
        // TextView to display result

        // Try to connect using Apache HttpClient Library
        try {
            httpclient = new DefaultHttpClient();
            request = new HttpGet(message[0]);
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

                // Appending result to textview
                result = result + line ;
            }
        } catch (Exception e) {
            // Code to handle exception
            result = "error";
        }
        return result;
    }

    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

    }

}