package com.neo.DatabaseModel.Message;

import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = MessageBase.MESSAGE+"")
public class Message extends MessageBase{

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders order;

    public Message(String message, User sender, Orders order) {
        super(message, sender);
        this.order = order;
    }

    public Message() {
    }

    public Message(String message, User sender) {
        super(message, sender);
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
