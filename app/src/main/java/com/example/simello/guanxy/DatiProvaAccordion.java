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
    public static ArrayList<Double> latitudes = new ArrayList<Double>();
    public static ArrayList<Double> longitudes = new ArrayList<Double>();

    public DatiProvaAccordion(ArrayList<String> dati)
    {
        //7 nome utente / header
        //6 Numero ID richiesta
        //3 testo della richiesta
        //11 lat
        //12 lon
        for(int i = 0 ; i < dati.size() ; i ++)
        {

            String [] parts = dati.get(i).split(",");

            headers.add(parts[7].replaceAll("\"",""));
            texts.add(parts[3].replaceAll("\"",""));
            ids.add(parts[6].replaceAll("\"",""));
            parts[16] = parts[16].replaceAll("\"","").trim();
            parts[17] = parts[17].replaceAll("\"","").trim();

            latitudes.add(Double.valueOf(parts[16]));
            longitudes.add(Double.valueOf(parts[17]));
        }


    }
    //Da finire di pulire...
    public static void clean()
    {
        headers.removeAll(headers);
        texts.removeAll(texts);
        ids.removeAll(ids);
        latitudes.removeAll(latitudes);
        longitudes.removeAll(longitudes);

    }
}
