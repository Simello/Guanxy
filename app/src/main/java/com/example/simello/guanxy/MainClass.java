package com.example.simello.guanxy;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.simello.classiServer.FindUserInput;
import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.registrazione.RegistrazioneTabActivity;
import com.example.simello.utils.GPSManager;
import com.example.simello.utils.utils;
import com.parse.Parse;




/**
 * Created by Sunfury on 01/03/15.
 */
public class MainClass extends Application
{
    private String mPhoneNumber;


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "f1m2IvzkUOSkch5vnJfZOpbB0Om6qp3iHa5K1o8e", "dik2XD2ZWEGU1MJahH0dtnbnT5KIVnI71NSxpykj");

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        //Prendo il numero di telefono
        mPhoneNumber = utils.numeroTelefonoCorrente(this);

        String code = prefs.getString("PIN", "PIN");
        // code.compareTo("PIN") == 0 || code.compareTo(""+mPhoneNumber) != 0  <---- Questp sarà l'if finale
        if (code.compareTo("PIN") == 0) {
            Intent i = new Intent(this, RegistrazioneTabActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            String username = prefs.getString("nickname", "");
            GPSManager gpsManager = new GPSManager(this);
            Position position = new Position((float) gpsManager.getLatitude(), (float) gpsManager.getLongitude());
            //Qui basta usare utils.numeroTelefonoCorrente(this); al posto del mio numero lel
            User.getIstance(username, this, mPhoneNumber, 0, position);
        }
    }

}
