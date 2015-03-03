package com.example.simello.registrazione;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.GuanxyActivity;
import com.example.simello.guanxy.R;
import com.example.simello.utils.GPSManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunfury on 03/03/15.
 */
public class RegistrazioneUsername extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_username);
        TextView textView = (TextView) findViewById(R.id.NewUsernameText);
        textView.setTextColor(getResources().getColor(R.color.white));


        Button inizio = (Button) findViewById(R.id.buttonInizia);
        inizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText usernameEditText = (EditText) findViewById(R.id.NewUsername);
                String sUsername = usernameEditText.getText().toString();
                if (sUsername.matches("")) {
                    Toast.makeText(RegistrazioneUsername.this, "Non hai inserito un nome utente corretto", Toast.LENGTH_SHORT).show();
                    return;
                }

                GPSManager gpsManager = new GPSManager(RegistrazioneUsername.this);

                Position position = new Position((float)gpsManager.getLatitude(),(float) gpsManager.getLongitude());
                List<Position> positions = new ArrayList<Position>();
                positions.add(position);
                User.getIstance(sUsername,RegistrazioneUsername.this,"3208814625",0,positions);
                User u = User.getUser();
                u.setNickname(sUsername);

                Intent i = new Intent(RegistrazioneUsername.this, GuanxyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("PIN","No");
                startActivity(i);

                //todo esegui script registrazione

            }
        });




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(this, GuanxyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
