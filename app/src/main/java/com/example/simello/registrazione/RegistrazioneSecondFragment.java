package com.example.simello.registrazione;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury on 01/03/15.
 */
public class RegistrazioneSecondFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registrazione_second_fragment, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setText(getArguments().getString("msg"));
        tv.setTypeface(Typeface.DEFAULT_BOLD);


        TextView inizio = (TextView) v.findViewById(R.id.inizio);
        inizio.setText(Html.fromHtml("<i><u>Premi per iniziare.</u></i>"));


        inizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),RegistrazioneUsername.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);

            }
        });

        return v;
    }



    public static RegistrazioneSecondFragment newInstance(String text) {

        RegistrazioneSecondFragment f = new RegistrazioneSecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
