package com.example.simello.classiServer;

/**
 * Created by Sunfury on 19/03/15.
 */
import java.math.BigInteger;

public class CancelHelpRequestInput extends BaseInput{

    /**
     *
     */
    private static final long serialVersionUID = -2899336296970114652L;
    private BigInteger id;
    private String idUser;

    public CancelHelpRequestInput(BigInteger id, String idUser)
    {
        this.id = id;
        this.idUser = idUser;
    }

    public BigInteger getId() {
        return id;
    }
    public void setId( BigInteger id ) {
        this.id = id;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser( String idUser ) {
        this.idUser = idUser;
    }



}