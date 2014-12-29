package com.example.simello.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simello.controller.punteggi.Punteggio;
import com.example.simello.controller.punteggi.Utente;
import com.example.simello.guanxy.R;

/**
 * Created by Sunfury & Simello on 29/12/14.
 */
public class Punti extends Fragment
{
    public static Punti newInstance()
    {
        return new Punti();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.punteggi_fragment, container, false);

        //Qui verrà implementato tutto il lavoro di Simone,
        //Quindi con creazione classe ControllerPunteggio dal quale verranno ricavati i campi da stampare
        //Quando sarà da ripetere per il pulsante Chiedi Aiuto, utilizza TabActivity
        //Problema, come ricava i campi Simone?? La classe di Simone nn è activity e non può esserla, quindi come si fa?
        TextView tUser = (TextView) rootView.findViewById(R.id.user);
        TextView tPunti = (TextView) rootView.findViewById(R.id.punti);
        Punteggio punti = new Punteggio();
        Utente user = new Utente("Sunfury");
        tUser.setText(user.getNome());
        punti.updatePoints();
        tPunti.setText(""+punti.getValore());
        return rootView;
    }
    //Ricontrolla i metodi onResume() del fragment

}

