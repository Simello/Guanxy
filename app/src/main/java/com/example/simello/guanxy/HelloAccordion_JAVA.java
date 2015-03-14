package com.example.simello.guanxy;

/**
 * Created by simello on 13/03/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;



public class HelloAccordion_JAVA extends Activity {
    LinearLayout buses;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accordion_dinamico);
        buses=(LinearLayout)findViewById(R.id.linearLayoutBuses);
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



    void fillCountryTable() {

        Button b1;
        TextView t1;
//Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < DatiProvaAccordion.headerProva.length; current++) {

            b1 = new Button(this);
            t1 = new TextView(this);

            b1.setId(current);
            t1.setId(current);

            b1.setText(DatiProvaAccordion.headerProva[current]);
            t1.setText(DatiProvaAccordion.textProva[current]);

            b1.setTypeface(null, 1);
            t1.setTypeface(null, 1);

            t1.setTextSize(15);
            b1.setTextSize(15);

            b1.setGravity(Gravity.CENTER_VERTICAL);
            t1.setGravity(Gravity.RIGHT);
            t1.setTextColor(getResources().getColor(R.color.white));

            t1.setPadding(20, 10, 10, 10);

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

//b1.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);

           // Drawable d = Drawable.createFromPath(getString(R.layout.login_selector));
          //  b1.setBackgroundColor(getResources().getColor(R.color.blueChiaro));

            buses.addView(b1, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            buses.addView(t1, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            b1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
//button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

                    Button button = (Button) v;
                    System.out.println(v.getId());
                    LinearLayout parent = (LinearLayout) v.getParent();

/**
 * It text color is black
 *         open the accordion of selected tab
 *         close the accordion of remaining tab
 * else
 *         if text color is white
 *         close the accordion of selected tab
 */
                    if(button.getCurrentTextColor() == getResources().getColor(R.color.biancoChiuso)){
/**
 * OPEN CHILD OF SELECTED TAB AND CLOSE REMAINING PREVIOUSLY OPENED TABS
 */
                        for(int j=0; j<parent.getChildCount(); j++)
                        {

                            if(v.getId() == parent.getChildAt(j).getId())
                            {

// Change color, so that we can distinguish the tab which are selected
                                button.setTextColor(getResources().getColor(R.color.white));

// Change visibility
                                parent.getChildAt(j).setVisibility(parent.getChildAt(j).VISIBLE);
                                //TODO prendere l'id di questo child aperto xkee dopo si perde e chiude il bottone sotto
// Chnage icon
                            /*    button.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.marker_icon_map,     //left
                                        0,      //top
                                        0,  //right
                                        0);     //bottom*/
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

                         //   TextView t2 = (TextView) findViewById(parent.getChildAt(j+1).getId());
                          //  Log.i("TESTO2", ""+((Button) v).getText());
                           // Log.i("TESTO", ""+v.getId());
                            //TextView y = (TextView)findViewById(v.getId()+1);
                            //Log.i("TESTO3", ""+y.getText());

              /*              Log.i("CHILD_id", "" + parent.getChildAt(j).getId());
                            TextView y = (TextView) findViewById(parent.getChildAt(j).getId());
                            Log.i("CHILD_content", ""+y.getText());*/

                            if(v.getId() == parent.getChildAt(j).getId() &&
                                    parent.getChildAt(j).getVisibility() == View.VISIBLE){

// Change color, so that we can distinguish the tab which are selected
                                button.setTextColor(getResources().getColor(R.color.biancoChiuso));

// Change visibility
                                Log.i("PARENT", ""+ parent.getChildAt(j+1).toString());
                                parent.getChildAt(j+1).setVisibility(parent.getChildAt(j + 1).GONE);

// Chnage icon
                               /* button.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.marker_icon_map,     //left
                                        0,      //top
                                        0,  //right
                                        0);     //bottom*/
                                Drawable simelloDbleChiuso2 = getResources().getDrawable(R.drawable.freccia_chiuso_sml);
                                simelloDbleChiuso2.setBounds(0,0,80,80);
                                button.setCompoundDrawables(simelloDbleChiuso2,null,null,null);
                            }

                        }
                    }
                }
            });
        }
    }
}