package com.neo.DatabaseModel.Message;

import com.neo.DatabaseModel.Users.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.INTEGER
)
public abstract class MessageBase {

    public static final int COMPLAINT=1, CHAT=2, MESSAGE=3;

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1024)
    private String message;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User sender;

    @Column(nullable = false)
    private long timestamp;

    public MessageBase() {
    }

    public MessageBase(String message) {
        this.message = message;
        timestamp=new Date().getTime();
    }

    public MessageBase(String message, User sender) {
        this.message = message;
        this.sender = sender;
        timestamp=new Date().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
