package com.example.simello.aiuta.gli.altri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury on 04/03/15.
 */
public class AiutoFragment extends Fragment
{
    public static AiutoFragment newIstance()
    {
        AiutoFragment aiutoFragment = new AiutoFragment();
        return aiutoFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_aiuto, container, false);
        return v;

    }


}
