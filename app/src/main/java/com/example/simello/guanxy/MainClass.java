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

    }

}
