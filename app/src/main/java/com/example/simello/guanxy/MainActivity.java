package com.example.simello.guanxy;

import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simello.controller.punteggi.Punteggio;
import com.example.simello.controller.punteggi.Utente;
import com.viewpagerindicator.TabPageIndicator;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static NoSwipeViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        if (isOnline())
        {
            //@Todo
            //Registrazione per il primo login o exit
        }
        else
        {
            //@Todo
            //Farlo connettere
            //Idea, prendo dal db i dati (quindi punti, user e giorni mancanti) e li inserisco in un Bundle, così
            //possiamo portarli in giro
        }
    */

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (NoSwipeViewPager) findViewById(R.id.pager);
        //Se vuoi abilitare lo swipe, basta settare a true
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FragmentGuanxy.newInstance();
                case 1:
                    return FragmentPunteggi.newInstance();
                case 2:
                    return FragmentGuida.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1);
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section3);
            }
            return null;
        }
    }

 // Creo un fragment per ogni Facciata da creare.
    public static class FragmentGuanxy extends Fragment
    {
        public static FragmentGuanxy newInstance()
        {
            return new FragmentGuanxy();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ImageButton btn = (ImageButton) rootView.findViewById(R.id.chiediAiuto);
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // here you set what you want to do when user clicks your button,
                    // e.g. launch a new activity
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack(null);
                    //transaction.replace(R.id.pager, FragmentTest.newInstance());
                    transaction.commit();
                    Toast.makeText(getActivity(), "Premuto!",Toast.LENGTH_SHORT ).show();
                    mSectionsPagerAdapter.notifyDataSetChanged();

                    //@Todo Creare una classe per ogni frammento, quindi creare un package di frammenti.
                    //2 package diversi, 1 per il ViewPager Principale (Guanxy,Punti,Guida)
                    //e uno per il secondo ViewPager(Richiesta,Mappa,Chat)

                }
            });
            return rootView;
        }


    }




    public static class FragmentPunteggi extends Fragment
    {
        public static FragmentPunteggi newInstance()
        {
            return new FragmentPunteggi();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.punteggi_fragment, container, false);

            //Qui verrà implementato tutto il lavoro di Simone,
            //Quindi con creazione classe ControllerPunteggio dal quale verranno ricavati i campi da stampare
            //Quando sarà da ripetere per il pulsante Chiedi Aiuto, utilizza TabActivity
            //Problema, come ricava i campi Simone?? La classe di Simone nn è activity e non può esserla, quindi come si fa?
            TextView tUser = (TextView) rootView.findViewById(R.id.user);
            TextView tPunti = (TextView) rootView.findViewById(R.id.punti);
            Punteggio punti = new Punteggio();
            Utente user = new Utente("Sunfury");
            tUser.setText(user.getNome());
            punti.updatePoints();
            tPunti.setText(""+punti.getValore());
            return rootView;
        }
            //Ricontrolla i metodi onResume() del fragment

    }


    public static class FragmentGuida extends Fragment
    {
        public static FragmentGuida newInstance()
        {
            FragmentGuida f = new FragmentGuida();
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.guida_fragment, container, false);
            return rootView;
        }


    }


    //Metodo per il controllo se il dispositivo è connesso Online
    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
