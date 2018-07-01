package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localadmin on 16/7/17.
 */
public class RChatBox {
    private RUser sender;
    private List<RMessage> chats=new ArrayList<>();

    public RChatBox() {
    }

    public void addMessage(String message, String sender, String time){
        chats.add(new RMessage(message, sender, time));
    }

    public RUser getSender() {
        return sender;
    }

    public void setSender(RUser sender) {
        this.sender = sender;
    }
}
