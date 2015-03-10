package com.example.simello.registrazione;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simello.classiServer.InsertUserInput;
import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.GuanxyActivity;
import com.example.simello.guanxy.R;
import com.example.simello.utils.AsyncConnection;
import com.example.simello.utils.GPSManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sunfury on 03/03/15.
 */
public class RegistrazioneUsername extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_username);
        TextView textView = (TextView) findViewById(R.id.NewUsernameText);
        textView.setTextColor(getResources().getColor(R.color.white));


        Button inizio = (Button) findViewById(R.id.buttonInizia);
        inizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText usernameEditText = (EditText) findViewById(R.id.NewUsername);
                //Prendo l'username
                String sUsername = usernameEditText.getText().toString();
                if (sUsername.matches("")) {
                    Toast.makeText(RegistrazioneUsername.this, "Non hai inserito un nome utente corretto", Toast.LENGTH_SHORT).show();
                    return;
                }

                GPSManager gpsManager = new GPSManager(RegistrazioneUsername.this);
                //Prendo la posizione
                Position position = new Position((float)gpsManager.getLatitude(),(float) gpsManager.getLongitude());
                //Creo l'oggetto
                InsertUserInput userInput = new InsertUserInput("3208814625",sUsername,(long)gpsManager.getLongitude(),(long)gpsManager.getLatitude());

                HashMap<String,Object> provaConnessione = new HashMap<String, Object>();
                provaConnessione.put("url","http://5.249.151.38:8080/guanxy/user");
                provaConnessione.put("User",userInput);
                AsyncConnection connection = new AsyncConnection(RegistrazioneUsername.this);
                connection.execute(provaConnessione);



                User.getIstance(sUsername,RegistrazioneUsername.this,"3208814625",0,position);
                User u = User.getUser();
                u.setNickname(sUsername);



                Intent i = new Intent(RegistrazioneUsername.this, GuanxyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("PIN","No");
                startActivity(i);

                //todo esegui script registrazione

            }
        });




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(this, GuanxyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
