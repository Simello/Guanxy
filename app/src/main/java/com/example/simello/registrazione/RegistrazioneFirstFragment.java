package com.example.simello.registrazione;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury on 01/03/15.
 */
public class RegistrazioneFirstFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registrazione_first_fragment, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static RegistrazioneFirstFragment newInstance(String text) {

        RegistrazioneFirstFragment f = new RegistrazioneFirstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
