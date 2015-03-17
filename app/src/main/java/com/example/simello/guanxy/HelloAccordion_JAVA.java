package com.example.simello.guanxy;

/**
 * Created by simello on 13/03/15.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.simello.classiServer.SearchHelpRequestInput;
import com.example.simello.controller.varie.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class HelloAccordion_JAVA extends ActionBarActivity {
    LinearLayout buses;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accordion_dinamico);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        User user = User.getUser();
        //creo l oggetto per cercare le richieste
        SearchHelpRequestInput userResearcher = new SearchHelpRequestInput(user.getIdUser(),user.getPosition().getLat(),user.getPosition().getLon());
        connectAsyncTask connectAsyncTask = new connectAsyncTask("http://5.249.151.38:8080/guanxy/searchRequest");
        connectAsyncTask.execute(userResearcher);

        buses=(LinearLayout)findViewById(R.id.linearLayoutBuses);



        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.i("SW", ""+ metrics.densityDpi);
        Log.i("SW", ""+ metrics.widthPixels);


        fillCountryTable();


        //REPARTO BOTTONI
        //Prendo il bottone e setto a true x lasciare il colore blue
        //DA RICORDARE
        //Quando si premeranno gli altri bottoni (Punti o Guida) il valore di pressed di Guanxy deve essere messo a false
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        //Prendo il singolo bottone
        final Button punti = (Button) findViewById(R.id.punti);

        final Button guida = (Button) findViewById(R.id.guida);

        //GESTIONE COLORE BOTTONI
        guanxy.setPressed(true);
        guanxy.setSelected(true);
        guanxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Guanxy, cambia il colore di Punti/Guida(da aggiungere)
                punti.setPressed(false);
                punti.setSelected(false);

                Intent myIntent = new Intent(HelloAccordion_JAVA.this, GuanxyActivity.class);

                HelloAccordion_JAVA.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                guanxy.setSelected(false);
                guanxy.setPressed(false);


                Intent myIntent = new Intent(HelloAccordion_JAVA.this, PuntiActivity.class);
                HelloAccordion_JAVA.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        guida.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v){
                v.setSelected(true);

                guanxy.setSelected(false);
                guanxy.setPressed(false);

                Intent myIntent = new Intent(HelloAccordion_JAVA.this, GuidaActivity.class);

                HelloAccordion_JAVA.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(0, 0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void fillCountryTable() {

        Button b1;
        TextView t1;
        Button b2;
        RelativeLayout rl1;
//Converting to dip unit
        //chissa che cazzo fa sta linea di codice
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < DatiProvaAccordion.headers.size(); current++)
        {
            rl1=new RelativeLayout(this);
            b1 = new Button(this);
            t1 = new TextView(this);
            b2 = new Button(this);


            rl1.addView(b2);

            b1.setId(current);
            t1.setId(current);
            rl1.setId(current);

            b1.setText(DatiProvaAccordion.headers.get(current));
            t1.setText(DatiProvaAccordion.texts.get(current));

            b1.setTypeface(null, 1);
            t1.setTypeface(null, 1);
            b2.setBackground(getResources().getDrawable(R.drawable.bottone_accetta_aiutaglialtri_bozza));
            t1.setTextSize(15);
            b1.setTextSize(15);


            b1.setGravity(Gravity.CENTER_VERTICAL);
            t1.setGravity(Gravity.LEFT | Gravity.TOP);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    350,
                    120
            );
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            b2.setLayoutParams(params);


            // IMPORTANTE PER CENTRARE IL TESTO MODIFICARE IL PRIMO VALORE ( 400 ) IN QUESTO CASO
            t1.setPadding(410, 90, 80, 50);
            t1.setTextColor(getResources().getColor(R.color.white));
            //b1.setPadding(10,5,10,10);
            b1.setBackgroundColor(getResources().getColor(R.color.blueChiaro));
            b1.setHeight(25);

            //<altro pezzo brutto>
            //forzo la dimensione dello sfondo alla dimensione dell' immagine
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.sfondo_nuvoletta);
            BitmapDrawable bgnuovo = new BitmapDrawable( bg.getBitmap());
            bgnuovo.setTileModeXY(bg.getTileModeX(), bg.getTileModeY());
            t1.setBackground(bgnuovo);
            //</altro pezzo brutto>
/**
 * By default colour of button is black
 */
            // <quanto so forte>
            b1.setTextColor(getResources().getColor(R.color.biancoChiuso));
            Drawable simelloDble = getResources().getDrawable(R.drawable.freccia_chiuso_sml);
            simelloDble.setBounds(0,0,80,80);
            b1.setCompoundDrawables(simelloDble,null,null,null);
            // </quanto so forte>
          /*  b1.setCompoundDrawablesWithIntrinsicBounds(
                    simelloDble,     //left
                    0,      //top
                    0,  //right
                    0);     //bottom*/
            t1.setVisibility(t1.GONE);
            rl1.setVisibility(rl1.GONE);
            buses.addView(b1, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            buses.addView(t1, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            buses.addView(rl1, LayoutParams.FILL_PARENT);


            b1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Button button = (Button) v;
                    System.out.println(v.getId());
                    LinearLayout parent = (LinearLayout) v.getParent();




                    for(int j=0; j<parent.getChildCount(); j++)
                    {

                        if (parent.getChildAt(j) instanceof Button) {
                            Button x = (Button) parent.getChildAt(j);
                            if (x.getCurrentTextColor() == getResources().getColor(R.color.white) && x.getId() != v.getId()) {
                                Log.i("CHIUDOBTN", ""+x.getId());
                                x.setTextColor(getResources().getColor(R.color.biancoChiuso));
                                parent.getChildAt(j+1).setVisibility(parent.getChildAt(j + 1).GONE);
                                parent.getChildAt(j+2).setVisibility(parent.getChildAt(j+2).GONE);
                                Drawable simelloDbleChiuso2 = getResources().getDrawable(R.drawable.freccia_chiuso_sml);
                                simelloDbleChiuso2.setBounds(0,0,80,80);
                                x.setCompoundDrawables(simelloDbleChiuso2,null,null,null);


                            }
                        }
                    }



/**
 * It text color is black
 *         open the accordion of selected tab
 *         close the accordion of remaining tab
 * else
 *         if text color is white
 *         close the accordion of selected tab
 *
 *
 *
 *
 */

                    if(button.getCurrentTextColor() == getResources().getColor(R.color.biancoChiuso))
                    {

                        for(int j=0; j<parent.getChildCount(); j++)
                        {
                            if(v.getId() == parent.getChildAt(j).getId())
                            {

                                button.setTextColor(getResources().getColor(R.color.white));
                                parent.getChildAt(j).setVisibility(parent.getChildAt(j).VISIBLE);

                                Drawable simelloDbleAperto = getResources().getDrawable(R.drawable.freccia_aperto_sml);
                                simelloDbleAperto.setBounds(0,0,80,80);
                                button.setCompoundDrawables(simelloDbleAperto,null,null,null);

                            }
                        }
                    }else{
/**
 * CLOSE TAB OF ACCORDION WHICH IS OPEN
 */
                        for(int j=0; j<parent.getChildCount(); j++)
                        {

                            if(v.getId() == parent.getChildAt(j).getId() &&
                                    parent.getChildAt(j).getVisibility() == View.VISIBLE)
                            {

// Change color, so that we can distinguish the tab which are selected
                                button.setTextColor(getResources().getColor(R.color.biancoChiuso));

// Change visibility
                                Log.i("CHIUDO", ""+parent.getChildAt(j)+"\n");
                                parent.getChildAt(j+1).setVisibility(parent.getChildAt(j + 1).GONE);
                                parent.getChildAt(j+2).setVisibility(parent.getChildAt(j+2).GONE);


// Chnage icon
                                //questa si potrebbe riutilizzare in certe circostanze aggiunge l'immagine a sinistra del
                                //bottone ma non puo' essere sovrascritta da testo o altro
                               /* button.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.marker_icon_map,     //left
                                        0,      //top
                                        0,  //right
                                        0);     //bottom*/
                                Log.i("Cambioicona", "");
                                Drawable simelloDbleChiuso2 = getResources().getDrawable(R.drawable.freccia_chiuso_sml);
                                simelloDbleChiuso2.setBounds(0,0,80,80);
                                button.setCompoundDrawables(simelloDbleChiuso2,null,null,null);
                                break;
                            }

                        }
                    }
                }
            });
        }
    }

    //CREO L'ASKYNCTASK PER LA CONNESSIONE
    private class connectAsyncTask extends AsyncTask<SearchHelpRequestInput, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        connectAsyncTask(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(HelloAccordion_JAVA.this);
            progressDialog.setMessage("Sto Cercando!");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(SearchHelpRequestInput... params) {

            SearchHelpRequestInput userResearcher = params[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(userResearcher);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


                Log.i("OBJECT", s);
                response = httpclient.execute(request);

                Log.i("Invio","fatto");


            }

            catch (Exception e) {
                // Code to handle exception
                result = "error";
            }

            // response code
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {

                    result = result + line ;
                }
                Log.d("Ritorno",result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("helpRequest");
                ArrayList<String> listdata = new ArrayList<String>();
                if (jsonArray != null) {
                    for (int i=0;i<jsonArray.length();i++){
                        listdata.add(jsonArray.get(i).toString());
                    }
                }
                new DatiProvaAccordion(listdata);

            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }


            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    }


}