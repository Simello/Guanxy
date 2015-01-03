package com.example.simello.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury & Simello on 29/12/14.
 */
public class ChiediAiuto extends Fragment
{
    public static ChiediAiuto newInstance()
    {
        return new ChiediAiuto();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chiedi_aiuto, container, false);
        return rootView;
    }
}
