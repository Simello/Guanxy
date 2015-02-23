package com.example.simello.controller.varie;

/**
 * Created by Sunfury on 23/02/15.
 */
public class Position
{
    private float lat;
    private float lon;

    public Position(float lat, float lon)
    {
        this.lat = lat;
        this.lon = lon;
    }


    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
