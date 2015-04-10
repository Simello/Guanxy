package com.example.simello.classiServer;

import java.util.List;

/**
 * Created by Sunfury on 10/04/15.
 */
public class MessageByTimeOutput extends BaseOutput{

    /**
     *
     */
    private static final long serialVersionUID = 6408891243734989777L;

    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages( List<Message> messages ) {
        this.messages = messages;
    }

}