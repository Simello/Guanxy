package com.example.simello.classiServer;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Sunfury on 10/04/15.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigInteger id;

    private String messageTxt;

    private Date messageTime;

    private String status;

    public Message() {
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt( String messageTxt ) {
        this.messageTxt = messageTxt;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime( Date messageTime ) {
        this.messageTime = messageTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

}