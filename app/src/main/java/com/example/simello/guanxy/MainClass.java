package com.example.simello.guanxy;

import android.app.Application;
import android.location.LocationManager;

import com.parse.Parse;


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
