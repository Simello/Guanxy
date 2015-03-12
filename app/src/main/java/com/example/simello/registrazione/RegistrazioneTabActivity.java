package com.example.simello.registrazione;

/**
 * Created by Sunfury on 01/03/15.
 */
import com.example.simello.guanxy.GuanxyActivity;
import com.example.simello.guanxy.R;
import com.example.simello.utils.utils;
import com.viewpagerindicator.CirclePageIndicator;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

public class RegistrazioneTabActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_layout_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return RegistrazioneFirstFragment.newInstance("Benvenuto in Guanxy!");
                case 1: return RegistrazioneSecondFragment.newInstance("Questo è Guanxy 1");
                case 2: return RegistrazioneSecondFragment.newInstance("Questo è Guanxy 2");
                case 3: return RegistrazioneSecondFragment.newInstance("Questo è Guanxy 3");
                case 4: return RegistrazioneSecondFragment.newInstance("Questo è Guanxy 4");

                default: return RegistrazioneFirstFragment.newInstance("");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
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