package com.example.simello.classiServer;

/**
 * Created by simello on 07/04/15.
 */
import java.math.BigInteger;
import java.util.Date;


public class MessageByTimeInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = -4168765750832562881L;

    private BigInteger idHelp;

    private String idUser1;
    private Date time;

    public MessageByTimeInput(BigInteger idHelp, String idUser1, Date time)
    {
        this.idHelp = idHelp;
        this.idUser1 = idUser1;
        this.time = time;
    }

    public BigInteger getIdHelp() {
        return idHelp;
    }
    public void setIdHelp( BigInteger idHelp ) {
        this.idHelp = idHelp;
    }
    public String getIdUser1() {
        return idUser1;
    }
    public void setIdUser1( String idUser1 ) {
        this.idUser1 = idUser1;
    }

    public Date getTime() {
        return time;
    }
    public void setTime( Date time ) {
        this.time = time;
    }
    @Override
    public String toString() {
        return "MessageByTimeInput [idHelp=" + idHelp + ", idUser1=" + idUser1 + ", time=" + time + "]";
    }

}