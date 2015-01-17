package com.example.simello.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.simello.guanxy.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sunfury & Simello on 29/12/14.
 */
public class Guanxy extends android.support.v4.app.Fragment
{
    public static Guanxy newInstance()
    {
        return new Guanxy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton btn = (ImageButton) rootView.findViewById(R.id.chiediAiuto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here you set what you want to do when user clicks your button,
                // e.g. launch a new activity
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("https://192.168.43.67:8080/guanxy/welcome/model");
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                HttpResponse response = null;
                StringBuilder builder = new StringBuilder();

                try{
                    response = client.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                    Toast.makeText(getActivity(),""+statusCode,Toast.LENGTH_SHORT);
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e("pippa", "Failed to download file" + statusCode);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


                /*
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.pager, ChiediAiuto.newInstance());
                transaction.commit();
                */
                //Toast.makeText(getActivity(), "Premuto!", Toast.LENGTH_SHORT).show();

                //@Todo Creare una classe per ogni frammento, quindi creare un package di frammenti.
                //2 package diversi, 1 per il ViewPager Principale (Guanxy,Punti,Guida)
                //e uno per il secondo ViewPager(Richiesta,Mappa,Chat)

            }
        });
        return rootView;
    }


}