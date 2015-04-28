package com.example.simello.aiuta.gli.altri;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.simello.classiServer.FindHelpRequestInput;
import com.example.simello.controller.varie.Richiesta;
import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewpagerindicator.TabPageIndicator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Sunfury e Simello on 04/03/15.
 */
public class TabAiutaGliAltri extends FragmentActivity
{
    public static FragmentManager fragmentManager;
    private ViewPager mViewPager;
    private static Socket socket;
    String host = "5.249.151.38";
    int port = 5000;
    PrintWriter socketOutput = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_aiuta_gli_altri);
        new SocketTask().execute();


        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();

        mViewPager = (ViewPager) findViewById(R.id.viewPagerAiutaGliAltri);
        FragmentPagerAdapter mMyFragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());


        mViewPager.setAdapter(mMyFragmentPagerAdapter);


        final TabPageIndicator mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
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
//Variabili per la fine ...
private ProgressTask progressTask;
private String status = "";
private FindHelpRequestInput findHelpRequestInput;
private boolean stop = false;

    @Override
    protected void onStart()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
             while(!stop)
             {
                 findHelpRequestInput = new FindHelpRequestInput(Richiesta.getRichiesta().getIdRichiesta(), User.getUser().getIdUser());
                 progressTask = new ProgressTask("http://5.249.151.38:8080/guanxy/findHelpId");
                 progressTask.execute(findHelpRequestInput);
                 try {
                     Thread.currentThread();
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }

             }

            }
        }).start();
        super.onStart();
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

    @Override
    protected  void onPause()
    {
        stop = true;
        super.onPause();
    }



    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return AiutoFragment.newIstance();
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
                case 0: return "Aiuto";
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

    private class SocketTask extends AsyncTask<Void ,Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0)
        {
            socket = new Socket();
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

    public static Socket getSocket()
    {
        if(socket != null)
            return socket;
        else
            return null;
    }



    private class ProgressTask extends AsyncTask<FindHelpRequestInput,Void,String> {
        String url;
        Intent i;

        ProgressTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(FindHelpRequestInput... arg0) {
            FindHelpRequestInput findHelpRequestInput = arg0[0];
            HttpClient httpclient;
            HttpPost request;
            HttpResponse response = null;
            String result = "";

            try {
                httpclient = new DefaultHttpClient();
                request = new HttpPost(url);

                ObjectMapper objectWriter = new ObjectMapper();

                String s = objectWriter.writeValueAsString(findHelpRequestInput);
                StringEntity se = new StringEntity(s);
                request.setEntity(se);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                response = httpclient.execute(request);
            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }

            // response code
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {

                    result = result + line;
                }
                JSONObject json = new JSONObject(result);
                JSONObject help = json.getJSONObject("help");
                status = help.getString("status");
                //Log.i("TestTabAGA", status);

            } catch (Exception e) {
                // Code to handle exception
                result = "error";
            }
            Log.d("Ritorno", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(status.compareTo("COMPLETED") == 0)
            {
                stop = true;
                Log.i("FINE","Ok fine richiesta");
                i = new Intent(TabAiutaGliAltri.this,RichiestaCompletata.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(0,0);
            }
            else if(status.compareTo("CANCELLED") == 0)
            {
                Log.i("CANCELLATO", "RICHIESTA cancellata");
            }

        }
    }

}

