package com.example.simello.registrazione;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury & Simello on 01/03/15.
 */
public class RegistrazioneSecondFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // View v = inflater.inflate(R.layout.registrazione_second_fragment, container, false);

      //  TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
       // tv.setTextColor(getResources().getColor(R.color.white));
        //tv.setText(getArguments().getString("msg"));
        //tv.setTypeface(Typeface.DEFAULT_BOLD);

        /* questa parte non serve piu' perche' l'ho integrata con tab activity gg isi lemon squisi
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
        });*/

        View v = null;
        String nF = getArguments().getString("msg");
        int nFint = Integer.parseInt(nF);
        if ( nFint == 1 ){v = inflater.inflate(R.layout.registrazione_second_fragment, container, false);};
        if ( nFint == 2 ){v = inflater.inflate(R.layout.registrazione_third_fragment, container, false);};
        if ( nFint == 3 ){v = inflater.inflate(R.layout.registrazione_fourth_fragment, container, false);};
        if ( nFint == 4 ){v = inflater.inflate(R.layout.registrazione_fifth_fragment, container, false);};
        if ( nFint == 5 ){v = inflater.inflate(R.layout.registrazione_second_fragment, container, false);};


        Log.i("PROVALEL", nF);
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
