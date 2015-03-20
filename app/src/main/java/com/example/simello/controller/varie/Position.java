package com.example.simello.controller.varie;

/**
 * Created by Sunfury on 23/02/15.
 */
public class Position
{
    private double lat;
    private double lon;

    public Position(double lat, double lon)
    {
        this.lat = lat;
        this.lon = lon;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
