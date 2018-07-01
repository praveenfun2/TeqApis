package com.neo.DatabaseModel.Message;

import com.neo.DatabaseModel.Users.User;

import javax.persistence.*;

/**
 * Created by localadmin on 16/7/17.
 */
@Entity
@DiscriminatorValue(value = MessageBase.CHAT+"")
public class Chat extends MessageBase {

    @Column(name = "is_read")
    private boolean read=false;

    @ManyToOne
    @JoinColumn
    private User receiver;

    public Chat(String message) {
        super(message);
    }

    public Chat(String message, User sender, User receiver) {
        super(message, sender);
        this.receiver=receiver;
    }

    public Chat() {
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
