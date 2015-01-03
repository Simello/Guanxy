package com.example.simello.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.simello.guanxy.R;

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
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.pager, ChiediAiuto.newInstance());
                transaction.commit();
                Toast.makeText(getActivity(), "Premuto!", Toast.LENGTH_SHORT).show();

                //@Todo Creare una classe per ogni frammento, quindi creare un package di frammenti.
                //2 package diversi, 1 per il ViewPager Principale (Guanxy,Punti,Guida)
                //e uno per il secondo ViewPager(Richiesta,Mappa,Chat)

            }
        });
        return rootView;
    }


}