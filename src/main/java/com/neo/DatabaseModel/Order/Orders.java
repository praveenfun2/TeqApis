package com.neo.DatabaseModel.Order;

import com.neo.DatabaseModel.Message.Message;
import com.neo.DatabaseModel.PaymentOrder;
import com.neo.DatabaseModel.Users.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
public class Orders {

    public static final int CARD =1, DESIGNER=2, COURIER=3;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long timestamp, sTimestamp;

    @Column
    protected Float price;

    @ManyToOne
    @JoinColumn
    protected PaymentOrder paymentOrder;

    @Column(insertable = false, updatable = false)
    protected int type;

    @Column
    protected int status;

    @OneToMany(mappedBy = "order")
    @OrderBy(value = "timestamp asc")
    private List<Message> messages;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private User provider, consumer;

    public Orders() {
    }

    public Orders(Float price, PaymentOrder paymentOrder) {
        this.price = price;
        this.paymentOrder = paymentOrder;
        timestamp =new Date().getTime();
        sTimestamp=new Date().getTime();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(PaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public Long getsTimestamp() {
        return sTimestamp;
    }

    public void setsTimestamp(Long sTimestamp) {
        this.sTimestamp = sTimestamp;
    }
}
