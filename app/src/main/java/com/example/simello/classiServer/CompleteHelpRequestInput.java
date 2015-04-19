package com.example.simello.classiServer;

import java.math.BigInteger;

public class CompleteHelpRequestInput extends BaseInput{

    /**
     *
     */
    private static final long serialVersionUID = 7263575658384425908L;

    private BigInteger idHelp;
    private String idUser;
    private int point;

    public CompleteHelpRequestInput(BigInteger idHelp, String idUser, int point)
    {
        this.idHelp = idHelp;
        this.idUser = idUser;
        this.point = point;
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
    public int getPoint() {
        return point;
    }
    public void setPoint( int point ) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "CompleteHelpRequestInput [idHelp=" + idHelp + ", idUser=" + idUser + ", point=" + point + "]";
    }

}