package com.example.simello.guanxy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by simello on 13/03/15.
 */
public class DatiProvaAccordion
{
    public static ArrayList<String> headers = new ArrayList<String>();
    public static ArrayList<String> texts = new ArrayList<String>() ;
    public static ArrayList<String> ids = new ArrayList<String>();
    public static ArrayList<Integer> latitudes = new ArrayList<Integer>();
    public static ArrayList<Integer> longitudes = new ArrayList<Integer>();

    public DatiProvaAccordion(ArrayList<String> dati)
    {
        //7 nome utente / header
        //0 Numero ID richiesta
        //3 testo della richiesta
        //11 lat
        //12 lon
        for(String s : dati)
        {
            String [] parts = s.split(",");
            headers.add(parts[7]);
            texts.add(parts[3]);
            ids.add(parts[0]);
            latitudes.add(Integer.parseInt(parts[11]));
            longitudes.add(Integer.parseInt(parts[12]));
        }


    }
    //Da finire di pulire...
    public static void clean()
    {
        headers.removeAll(headers);
    }
}
