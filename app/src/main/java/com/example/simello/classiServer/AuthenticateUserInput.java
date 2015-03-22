package com.example.simello.classiServer;

/**
 * Created by Sunfury on 22/03/15.
 */
public class AuthenticateUserInput extends BaseInput{

    /**
     *
     */
    private static final long serialVersionUID = 5403417086521830907L;

    private String userId;
    private String pin;

    public AuthenticateUserInput(String userId, String pin)
    {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId( String userId ) {
        this.userId = userId;
    }
    public String getPin() {
        return pin;
    }
    public void setPin( String pin ) {
        this.pin = pin;
    }
}
