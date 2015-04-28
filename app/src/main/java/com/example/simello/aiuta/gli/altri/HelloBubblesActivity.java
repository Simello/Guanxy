package com.example.simello.aiuta.gli.altri;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.simello.controller.varie.Richiesta;
import com.example.simello.guanxy.R;
import com.example.simello.utils.GestoreChat;
import com.readystatesoftware.viewbadger.BadgeView;
import com.viewpagerindicator.TabPageIndicator;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Sunfury e simello on 04/03/15.
 */

public class HelloBubblesActivity extends Fragment {
    private DiscussArrayAdapter adapter;
    private ListView lv;
    private String ipsum;
    private EditText editText1;
    private Button btnSend;
    private GestoreChat Gc;
    private ArrayList<Double> messagesReceived;
    public BadgeView badgeView;
    public TabPageIndicator indicator;
    public static boolean isVisibile;
    public static boolean aiutato = false;
    private static Socket socket;
    private static DataInputStream socketInput = null;
    private static PrintWriter socketOutput = null;



    public static HelloBubblesActivity newIstance()
    {
        HelloBubblesActivity hba = new HelloBubblesActivity();
        return hba;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discuss, container, false);
        lv = (ListView)view.findViewById(R.id.listView1);

        Richiesta richiesta = Richiesta.getRichiesta();

        Gc = new GestoreChat( richiesta.getIdRichiesta());
        if(aiutato) {
            indicator = (TabPageIndicator) getActivity().findViewById(R.id.indicatorChiediAiuto);
            socket = TabChiediAiuto.getSocket();
            socketInput = TabChiediAiuto.getDataInputStream();
            socketOutput = TabChiediAiuto.getPrintWriter();
        }
        else
        {
            indicator = (TabPageIndicator) getActivity().findViewById(R.id.indicator);
            socket = TabAiutaGliAltri.getSocket();
            socketInput = TabAiutaGliAltri.getDataInputStream();
            socketOutput = TabAiutaGliAltri.getPrintWriter();
        }
        badgeView = new BadgeView(getActivity() , indicator);


        messagesReceived = new ArrayList<Double>();


        adapter = new DiscussArrayAdapter(getActivity(), R.layout.listitem_discuss);
        lv.setAdapter(adapter);

        editText1 = (EditText)view.findViewById(R.id.chatText);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    adapter.add(new OneComment(false, editText1.getText().toString()));
                    editText1.setText("");
                    return true;
                }
                return false;
            }
        });

        btnSend = (Button)view.findViewById(R.id.buttonSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se il campo testo è vuoto, non invia isi
                if(editText1.getText().toString().trim().length() > 0) {
                    adapter.add(new OneComment(false, editText1.getText().toString()));
                    //Gc.nuovoMessaggio(editText1.getText().toString());//invio msg al servere lelled lelling bicces madaffakka
                    socketOutput.println(editText1.getText().toString());
                    socketOutput.flush();
                    editText1.setText("");
                    lv.setSelection(lv.getAdapter().getCount() - 1);

                }
            }
        });


        //controllaMessaggi();
        ReadAsync readAsync = new ReadAsync();
        readAsync.execute();
        return  view;
    }

    private boolean stop = false;


    @Override
    public void onDetach()
    {
        stop = true;
        super.onDetach();
    }


    String risposta = "";
    private class ReadAsync extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try {
                while(!stop)
                {
                    risposta = socketInput.readLine();
                    if(risposta != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // change UI elements here
                                if(!isVisibile) {
                                    if (badgeView.isShown()) {
                                        badgeView.increment(1);
                                    } else {
                                        badgeView.setText("1");
                                        badgeView.show();
                                    }
                                }
                                adapter.add(new OneComment(true, risposta));
                                lv.setSelection(lv.getAdapter().getCount() - 1);

                            }
                        });
                    }
                    if(isVisibile)
                    {
                        if(badgeView.isShown())
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    badgeView.hide();
                                    badgeView.setText(""+ 0);
                                }

                            });

                        }

                    }
                }


            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }


}