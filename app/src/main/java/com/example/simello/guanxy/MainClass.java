package com.example.simello.guanxy;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by Sunfury on 01/03/15.
 */
public class MainClass extends Application
{
    private static MainClass s_instance;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "f1m2IvzkUOSkch5vnJfZOpbB0Om6qp3iHa5K1o8e", "dik2XD2ZWEGU1MJahH0dtnbnT5KIVnI71NSxpykj");


        // start the initial Activity
//        Intent i = new Intent(this, GuanxyActivity.class);
  //      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //    startActivity(i);
    }

    public MainClass()
    {
        s_instance = this;
    }

    public static MainClass getApplication()
    {
        return s_instance;
    }
}
