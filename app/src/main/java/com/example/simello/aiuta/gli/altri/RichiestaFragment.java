package com.example.simello.aiuta.gli.altri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury on 10/04/15.
 */
public class RichiestaFragment extends Fragment{
    public static RichiestaFragment newIstance()
    {
        RichiestaFragment richiestaFragment = new RichiestaFragment();
        return richiestaFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_richiesta, container, false);
        return v;

    }
}
