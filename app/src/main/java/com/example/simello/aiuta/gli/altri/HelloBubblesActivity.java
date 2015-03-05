package com.example.simello.aiuta.gli.altri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.simello.guanxy.R;

/**
 * Created by Sunfury e simello on 04/03/15.
 */

public class HelloBubblesActivity extends Fragment {
    private DiscussArrayAdapter adapter;
    private ListView lv;
    private String ipsum;
    private EditText editText1;
    private Button btnSend;


    public static HelloBubblesActivity newIstance()
    {
        HelloBubblesActivity hba = new HelloBubblesActivity();
        return hba;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discuss, container, false);
        lv = (ListView)view.findViewById(R.id.listView1);

        adapter = new DiscussArrayAdapter(getActivity(), R.layout.listitem_discuss);
        lv.setAdapter(adapter);

        editText1 = (EditText)view.findViewById(R.id.chatText);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //adapter.add(new OneComment(false, editText1.getText().toString()));
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
                adapter.add(new OneComment(false, editText1.getText().toString()));
                receiveMessage();
                editText1.setText("");
            }
        });

        return  view;
    }




    private void receiveMessage(){
        String msg = editText1.getText().toString();
        //new ChatDAO().receiveMessage("");
    }


    /** recebe msg */
    private void addItems() {
        adapter.add(new OneComment(true, "Hello bubbles!"));
    }

}