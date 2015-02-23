package com.example.simello.controller.varie;

/**
 * Created by simello & sunfury on 19/12/14.
 */
public class Punteggio
{
    private int valore;

    public Punteggio()
    {
        this.valore = 0;
    }

    public int getValore() {
        return valore;
    }

    public void updatePoints() {
       //@Todo: "chiamo script per aggiornamento punti"
        this.valore += 100;
    }
}
