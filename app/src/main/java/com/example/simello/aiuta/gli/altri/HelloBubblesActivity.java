package com.example.simello.aiuta.gli.altri;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simello.controller.varie.Richiesta;
import com.example.simello.guanxy.R;
import com.example.simello.utils.GestoreChat;
import com.readystatesoftware.viewbadger.BadgeView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;

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

        indicator = (TabPageIndicator) getActivity().findViewById(R.id.indicator);
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

                    receiveMessage();
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
                    Gc.nuovoMessaggio(editText1.getText().toString());//invio msg al servere lelled lelling bicces madaffakka
                    receiveMessage();
                    editText1.setText("");
                    lv.setSelection(lv.getAdapter().getCount() - 1);

                }
            }
        });


        controllaMessaggi();
        return  view;
    }

    private boolean stop = false;
    private HashMap<Double,String> messages;

    private void controllaMessaggi()
    {
        Thread thread;


        thread = new Thread(new Runnable(){
            public void run() {
                boolean newMessages = false;
                // TODO Auto-generated method stub
                while(!stop)
                {
                    try {
                        newMessages = Gc.controlla();
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if(newMessages)
                    {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messages = Gc.getMessages();
                                for(Double key : messages.keySet())
                                {
                                    if(!messagesReceived.contains(key))
                                    {
                                        if(!isVisibile)
                                        {
                                            badgeView.setText("1");
                                            badgeView.show();
                                        }
                                        messagesReceived.add(key);
                                        adapter.add(new OneComment(true, messages.get(key)));
                                        lv.setSelection(lv.getAdapter().getCount() - 1);
                                    }
                                }

                            }
                        });
                        newMessages = false;
                    }

                }

            }
        });
        thread.start();
    }



    @Override
    public void onDetach()
    {
        stop = true;
        super.onDetach();
    }


    private void receiveMessage(){
        String msg = editText1.getText().toString();
        lv.setSelection(lv.getAdapter().getCount()-1);

        //new ChatDAO().receiveMessage("");
    }


    /** recebe msg */
    private void addItems() {
        adapter.add(new OneComment(true, "Hello bubbles!"));
    }

}