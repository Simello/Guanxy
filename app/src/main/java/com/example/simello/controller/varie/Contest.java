package com.example.simello.controller.varie;


/**
 * Created by simello & sunfury on 19/12/14.
 */
public class Contest
{
    private static int NumContests = 0;
    private Premio premio;

    public int getNumContests() {
        return NumContests;
    }

    public void setNumContests() {
        NumContests++;
    }

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }
}
