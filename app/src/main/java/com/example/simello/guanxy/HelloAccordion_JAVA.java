package com.example.simello.guanxy;


import android.app.Activity;
import android.os.Bundle;
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

            b1.setGravity(Gravity.LEFT);
            t1.setGravity(Gravity.LEFT);
            t1.setTextColor(getResources().getColor(R.color.white));

            t1.setPadding(20, 10, 10, 10);


            t1.setBackground(getResources().getDrawable(R.drawable.sfondo_nuvoletta));

/**
 * By default colour of button is black
 */
            b1.setTextColor(getResources().getColor(R.color.nero));

            b1.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.marker_icon_map,     //left
                    0,      //top
                    0,  //right
                    0);     //bottom
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
                    if(button.getCurrentTextColor() == getResources().getColor(R.color.nero)){
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
                                parent.getChildAt(j+1).setVisibility(parent.getChildAt(j+1).VISIBLE);
                                //TODO prendere l'id di questo child aperto xkee dopo si perde e chiude il bottone sotto
// Chnage icon
                                button.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.marker_icon_map,     //left
                                        0,      //top
                                        0,  //right
                                        0);     //bottom
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

                            if(v.getId() == parent.getChildAt(j).getId() &&
                                    parent.getChildAt(j+1).getVisibility() == View.VISIBLE){

// Change color, so that we can distinguish the tab which are selected
                                button.setTextColor(getResources().getColor(R.color.nero));

// Change visibility

                                parent.getChildAt(j+1).setVisibility(parent.getChildAt(j + 1).GONE);

// Chnage icon
                                button.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.marker_icon_map,     //left
                                        0,      //top
                                        0,  //right
                                        0);     //bottom
                            }
                        }
                    }
                }
            });
        }
    }
}