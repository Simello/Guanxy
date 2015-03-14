package com.example.simello.classiServer;

/**
 * Created by Sunfury on 13/03/15.
 */
public class FindUserInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = -8886008247570971800L;

    private String id;

    public FindUserInput(String id)
    {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

}