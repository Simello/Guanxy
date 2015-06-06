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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sunfury on 11/04/15.
 */
public class CompletaRichiesta extends Activity {

    private RatingBar ratingBar;
    private PrintWriter socketOutput;
    private JSONObject datiInvio;
    private Socket socket;
    private Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        socketOutput = TabChiediAiuto.getPrintWriter();
        datiInvio = new JSONObject();
        socket = TabChiediAiuto.getSocket();

        setContentView(R.layout.completa_richiesta);

        ratingBar = (RatingBar) findViewById(R.id.ratingCompleta);
        Button invia = (Button) findViewById(R.id.buttonInviaCompletaRichiesta);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(CompletaRichiesta.this, GuanxyActivity.class);

                Richiesta richiesta = Richiesta.getRichiesta();
                int point = Math.round(ratingBar.getRating() * 2);
                try {
                    datiInvio.put("Fine", richiesta.getIdUser()); //Inserisco l'utente
                    datiInvio.put("Punti", point);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socketOutput.println(datiInvio.toString());
                socketOutput.flush();
                try {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                startActivity(i);


            }
        });


    }

}
