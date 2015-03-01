package com.example.simello.guanxy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.simello.controller.varie.Position;
import com.example.simello.controller.varie.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by simello e sunfury on 01/03/15.
 */
public class MappaActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        User user = User.getUser();
        Position pos = user.lcmastPosition();
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = fm.getMap();

        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(pos.getLat(),pos.getLon())).title("SIMELLO OP"));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(marker.getPosition());
        LatLngBounds baundese = builder.build();//isi
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(baundese,10);
        map.moveCamera(cu);
    }



}
