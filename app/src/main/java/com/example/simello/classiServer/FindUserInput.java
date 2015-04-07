package com.example.simello.classiServer;

/**
 * Created by Sunfury on 13/03/15.
 */
public class FindUserInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = -8886008247570971800L;

    private String idUser;

    public FindUserInput(String idUser)
    {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser( String idUser ) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "FindUserInput [idUser=" + idUser + "]";
    }

}