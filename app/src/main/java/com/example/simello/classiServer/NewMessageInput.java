package com.example.simello.classiServer;

/**
 * Created by simello on 07/04/15.
 */
import java.math.BigInteger;


public class NewMessageInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = 8385642764794610466L;


    private BigInteger idHelp;

    private String idUser;

    private String messageTxt;

    public NewMessageInput(String idHelp, String idUser, String messageTxt)
    {
        this.idUser = idUser;
        this.messageTxt = messageTxt;
        this.idHelp = new BigInteger(idHelp);
    }

    public BigInteger getIdHelp() {
        return idHelp;
    }

    public void setIdHelp(BigInteger idHelp) {
        this.idHelp = idHelp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    @Override
    public String toString() {
        return "NewMessageInput [idHelp=" + idHelp + ", idUser=" + idUser + ", messageTxt=" + messageTxt + "]";
    }

}
