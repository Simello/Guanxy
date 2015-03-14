package com.example.simello.classiServer;

import java.math.BigInteger;

public class FindHelpRequestInput extends BaseInput {

    /**
     *
     */
    private static final long serialVersionUID = 4971280391495882185L;
    private BigInteger id;

    public FindHelpRequestInput(BigInteger id)
    {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId( BigInteger id ) {
        this.id = id;
    }

}
