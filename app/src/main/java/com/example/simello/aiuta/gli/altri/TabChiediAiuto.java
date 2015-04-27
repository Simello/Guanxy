package com.example.simello.aiuta.gli.altri;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.simello.controller.varie.Richiesta;
import com.example.simello.guanxy.R;
import com.viewpagerindicator.TabPageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Sunfury on 10/04/15.
 */
public class TabChiediAiuto extends FragmentActivity {
    public static FragmentManager fragmentManager;
    private ViewPager mViewPager;
    private Socket socket = new Socket();
    String host = "5.249.151.38";
    int port = 5000;
    PrintWriter socketOutput = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_chiedi_aiuto);
        new SocketTask().execute();


        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();

        mViewPager = (ViewPager) findViewById(R.id.viewPagerChiediAiuto);
        FragmentPagerAdapter mMyFragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());


        mViewPager.setAdapter(mMyFragmentPagerAdapter);

        HelloBubblesActivity.aiutato = true;

        final TabPageIndicator mIndicator = (TabPageIndicator)findViewById(R.id.indicatorChiediAiuto);
        mIndicator.setViewPager(mViewPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != 2)
                    HelloBubblesActivity.isVisibile = false;
                else
                    HelloBubblesActivity.isVisibile = true;

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                if (state == ViewPager.SCROLL_STATE_IDLE)
                {
                    if (mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 0)
                    {
                        // Hide the keyboard.

                        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
                    }
                }
            }
        });

        mViewPager.setCurrentItem(1);




    }

    @Override
    protected void onStop()
    {
        super.onStop();
        try
        {
            socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private class SocketTask extends AsyncTask<Void ,Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                socket.connect(new InetSocketAddress(host,port));
                socketOutput = new PrintWriter( socket.getOutputStream(), true);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("IdRichiesta", Richiesta.getRichiesta().getIdRichiesta().toString());
                socketOutput.println(jsonObject.toString());
                socketOutput.flush();
                Log.i("Socket","Connesso");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

    }



    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return RichiestaFragment.newIstance();
                case 1: return MappaFragment.newIstance();
                case 2: return HelloBubblesActivity.newIstance();



                default:
                    return null;

            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position)
            {
                case 0: return "Richiesta";
                case 1: return "Mappa";
                case 2: return "Chat";
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
