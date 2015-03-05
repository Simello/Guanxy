package com.example.simello.aiuta.gli.altri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.simello.guanxy.R;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by Sunfury on 04/03/15.
 */
public class TabAiutaGliAltri extends FragmentActivity
{
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_aiuta_gli_altri);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPagerAiutaGliAltri);
        FragmentPagerAdapter mMyFragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mMyFragmentPagerAdapter);

        TitlePageIndicator mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);

        mViewPager.setCurrentItem(1);

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

}

