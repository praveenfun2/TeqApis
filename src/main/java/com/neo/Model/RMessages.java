package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

public class RMessages {
    private List<RMessage> messages=new ArrayList<>();
    private Long orderId;

    public RMessages() {
    }

    public void addMessage(String message, String sender, String time){
        messages.add(new RMessage(message, sender, time));
    }

    public RMessages(Long orderId) {
        this.orderId = orderId;
    }

    public List<RMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<RMessage> messages) {
        this.messages = messages;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
