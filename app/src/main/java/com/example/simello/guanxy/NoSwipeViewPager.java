package com.example.simello.guanxy;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Sunfury & Simello on 19/12/14.
 */

//Classe modificata del ViewPager che impedisce lo swipe a destra e sinistra x il cambio di visuale!
//Giovanni lo vuole così!
public class NoSwipeViewPager extends ViewPager
{
    private boolean isSwipeEnabled;

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isSwipeEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isSwipeEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isSwipeEnabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    /**
     * Custom method to enable or disable swipe
     *
     * @param isSwipeEnabled true to enable swipe, false otherwise
     *
     */
    public void setPagingEnabled(boolean isSwipeEnabled) {
        this.isSwipeEnabled = isSwipeEnabled;
    }
}
