package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

public class RComplaints {
    private List<RMessage> messages=new ArrayList<>();
    private Long orderId, ticketId;
    private boolean canResolve;
    private String reason;

    public RComplaints(Long orderId, Long ticketId, boolean canResolve, String reason) {
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.canResolve = canResolve;
        this.reason = reason;
    }

    public RComplaints(Long orderId, Long ticketId, boolean canResolve) {
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.canResolve = canResolve;
    }

    public void addMessage(String message, String sender, String time){
        messages.add(new RMessage(message, sender, time));
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isCanResolve() {
        return canResolve;
    }

    public List<RMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<RMessage> messages) {
        this.messages = messages;
    }

    public void setCanResolve(boolean canResolve) {
        this.canResolve = canResolve;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
