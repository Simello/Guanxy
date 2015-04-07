package com.example.simello.classiServer;

/**
 * Created by simello on 07/04/15.
 */
import java.math.BigInteger;


public class AllMessageInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = 914273962039439405L;


    private BigInteger idHelp;

    private String idUser1;

    private String idUser2;


    public BigInteger getIdHelp() {
        return idHelp;
    }

    public void setIdHelp(BigInteger idHelp) {
        this.idHelp = idHelp;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    @Override
    public String toString() {
        return "AllMessageInput [idHelp=" + idHelp + ", idUser1=" + idUser1 + ", idUser2=" + idUser2 + "]";
    }
}
