package com.example.simello.classiServer;

import java.math.BigInteger;

public class FindHelpRequestInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = 4971280391495882185L;
    private BigInteger idHelp;
    private String idUser;

    public FindHelpRequestInput(BigInteger idHelp, String idUser)
    {
        this.idHelp = idHelp;
        this.idUser = idUser;
    }

    public BigInteger getIdHelp() {
        return idHelp;
    }

    public void setIdHelp( BigInteger idHelp ) {
        this.idHelp = idHelp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser( String idUser ) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "FindHelpRequestInput [idHelp=" + idHelp + ", idUser=" + idUser + "]";
    }

}
