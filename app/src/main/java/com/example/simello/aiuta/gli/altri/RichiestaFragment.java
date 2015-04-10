package com.example.simello.aiuta.gli.altri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simello.controller.varie.Richiesta;
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
        Richiesta richiesta = Richiesta.getRichiesta();

        TextView finisciRichiesta = (TextView) v.findViewById(R.id.textCompletaRichiesta);
        finisciRichiesta.setText(getResources().getString(R.string.RichiestaPartOne) + richiesta.getIdUser() + "\n" + getResources().getString(R.string.RichiestaPartTwo));
        return v;

    }
}
