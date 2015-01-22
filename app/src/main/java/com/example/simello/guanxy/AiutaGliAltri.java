package com.example.simello.guanxy;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by sunfury & simello on 22/01/15.
 */
public class AiutaGliAltri extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aiuta_gli_altri);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(0,0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
