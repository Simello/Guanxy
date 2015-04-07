package com.example.simello.controller.varie;

import java.math.BigInteger;

/**
 * Created by Sunfury on 07/04/15.
 */
public class Richiesta
{
    private String idUser;
    private BigInteger idRichiesta;
    private double lat;
    private double lon;
    static Richiesta richiesta;

    public Richiesta(String idUser, String idRichiesta , double lat, double lon)
    {
        this.idUser = idUser;
        this.idRichiesta = new BigInteger(idRichiesta);
        this.lat = lat;
        this.lon = lon;

    }

    public static void newRichiesta(String idUser, String idRichiesta , double lat, double lon)
    {
        richiesta = new Richiesta(idUser, idRichiesta, lat, lon);
    }


}
