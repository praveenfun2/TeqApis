package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by localadmin on 22/6/17.
 */

@Entity
public class Notification {

    public static final String TYPE_CUSTOMER_CARD_ORDER="customer_card_order";
    public static final String TYPE_CUSTOMER_DESIGNER_ORDER="customer_design_order";
    public static final String TYPE_DESIGNER_ORDER="designer_order";
    public static final String TYPE_SELLER_CARD_ORDER = "seller_card_order";
    public static final String TYPE_SELLER_APPROVAL = "approval";

    public static final String MESSAGE_NEW_ORDER="New Order";
    public static final String MESSAGE_ORDER_UPDATE="Order Update";
    public static final String MESSAGE_ORDER_CANCELED="Order Canceled";
    public static final String MESSAGE_NEW_BID="Someone has bid on your offer";
    public static final String MESSAGE_ORDER_DELIVERED="Order completed";
    public static final String MESSAGE_CARD_DESIGNED="SellerCard designed";
    public static final String MESSAGE_DESIGNER_NEW_ORDER="Offer Accepted";
    public static final String MESSAGE_SELLER_CATEGORY_REQUEST_ACCEPTED = "Category request accepted";
    public static final String MESSAGE_SELLER_CATEGORY_REQUEST_REJECTED = "Category request rejected";
    public static final String MESSAGE_SELLER_SUB_CATEGORY_REQUEST_REJECTED = "Sub category request rejected";
    public static final String MESSAGE_SELLER_SUB_CATEGORY_REQUEST_ACCEPTED = "Sub category request accepted";
    public static final String MESSAGE_SELLER_PAPER_REQUEST_REJECTED = "Paper quality request rejected";
    public static final String MESSAGE_SELLER_PAPER_REQUEST_ACCEPTED = "Paper quality request accepted";
    public static final String MESSAGE_SELLER_FINISH_REQUEST_ACCEPTED = "Paper finish request accepted";
    public static final String MESSAGE_SELLER_FINISH_REQUEST_REJECTED = "Paper finish request rejected";

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String message;

    @Column(name = "is_read")
    private boolean read=false;

    @Column
    private long timestamp=new Date().getTime();

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Notification() {
    }

    public Notification(String message, String type, User user) {
        this.message = message;
        this.type = type;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
