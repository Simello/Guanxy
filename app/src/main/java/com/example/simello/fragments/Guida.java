package com.example.simello.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury & Simello on 29/12/14.
 */
public class Guida extends Fragment
{
    public static Guida newInstance()
    {
        Guida f = new Guida();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guida_fragment, container, false);
        return rootView;
    }


}