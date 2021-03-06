package com.example.simello.aiuta.gli.altri;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.simello.controller.varie.Richiesta;
import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.R;
import com.readystatesoftware.viewbadger.BadgeView;
import com.viewpagerindicator.TabPageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private EditText editText1;
    private Button btnSend;
    public BadgeView badgeView;
    public TabPageIndicator indicator;
    public static boolean isVisibile;
    public static boolean aiutato = false;
    private static Socket socket;
    private static DataInputStream socketInput = null;
    private static PrintWriter socketOutput = null;
    BufferedReader bufferUser = null;
    public JSONObject invio = null;
    private static HashMap<String,String> chat; //Non credo vada bene un HashMap... Forse una struttura di tipo LIFO?



    public static HelloBubblesActivity newIstance()
    {
        HelloBubblesActivity hba = new HelloBubblesActivity();
        return hba;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discuss, container, false);
        lv = (ListView)view.findViewById(R.id.listView1);

        invio = new JSONObject();
        try {
            invio.put("Invia", User.getUser().getIdUser().toString());
            invio.put("IdRichiesta", Richiesta.getRichiesta().getIdRichiesta());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }


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



        adapter = new DiscussArrayAdapter(getActivity(), R.layout.listitem_discuss);
        lv.setAdapter(adapter);

        editText1 = (EditText)view.findViewById(R.id.chatText);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if(editText1.getText().toString().trim().length() > 0)
                    {
                        adapter.add(new OneComment(false, editText1.getText().toString()));
                        //Invio messaggio
                        try{
                            invio.put("Text",editText1.getText().toString());
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                        socketOutput.println(invio.toString());
                        socketOutput.flush();
                        invio.remove("Text"); //Pulisco il JSON
                        //Inviato
                        editText1.setText("");
                        lv.setSelection(lv.getAdapter().getCount() - 1);


                    }
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
                    //Invio messaggio
                    try{
                        invio.put("Text",editText1.getText().toString());
                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }
                    socketOutput.println(invio.toString());
                    socketOutput.flush();
                    invio.remove("Text"); //Pulisco il JSON
                    //Inviato
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
                bufferUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while(!stop)
                {
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
                    //Risolto con bufferUser.ready()
                    if(bufferUser.ready()) {
                        risposta = socketInput.readLine();
                        //Non ottimizzato...
                        if(!aiutato && risposta.compareTo("___RichiestaCompletata_-_@_-_TermineOperazione!___") == 0)
                        {
                            socket.close();
                            Intent i = new Intent(getActivity(), RichiestaCompletata.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);


                        }


                        if (risposta != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // change UI elements here
                                    if (!isVisibile) {
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