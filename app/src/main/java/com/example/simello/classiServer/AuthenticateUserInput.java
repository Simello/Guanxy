package com.example.simello.classiServer;

/**
 * Created by Sunfury on 22/03/15.
 */
public class AuthenticateUserInput extends BaseInput{

    /**
     *
     */
    private static final long serialVersionUID = 5403417086521830907L;

    private String idUser;
    private String pin;

    public AuthenticateUserInput(String userId, String pin)
    {
        this.idUser = userId;
        this.pin = pin;
    }

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser( String idUser ) {
        this.idUser = idUser;
    }
    public String getPin() {
        return pin;
    }
    public void setPin( String pin ) {
        this.pin = pin;
    }
    @Override
    public String toString() {
        return "AuthenticateUserInput [idUser=" + idUser + ", pin=" + pin + "]";
    }
}
