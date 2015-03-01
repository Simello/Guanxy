package com.example.simello.registrazione;

/**
 * Created by Sunfury on 01/03/15.
 */
import com.example.simello.guanxy.R;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class RegistrazioneTabActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_layout_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return RegistrazioneFirstFragment.newInstance("FirstFragment, Instance 1");
                case 1: return RegistrazioneFirstFragment.newInstance("Prova2");

                default: return RegistrazioneFirstFragment.newInstance("lel");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}