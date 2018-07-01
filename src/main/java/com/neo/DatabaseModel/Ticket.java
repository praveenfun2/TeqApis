package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Message.Complaint;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by localadmin on 11/7/17.
 */

@Entity
public class Ticket {

    public static final int REASON_1_KEY=1, REASON_2_KEY=2, REASON_3_KEY=3;
    public static final String REASON_1_VALUE="Reason 1", REASON_2_VALUE="Reason 2", REASON_3_VALUE="Reason 3";
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int reason;

    @Column
    private boolean resolved=false;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Orders order;

    @Column(nullable = false)
    private long timestamp=new Date().getTime();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    @OrderBy(value = "timestamp asc")
    private List<Complaint> complaints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User issuer;

    public Ticket() {
    }

    public Ticket(int reason, Orders order, User issuer) {
        this.reason=reason;
        this.order = order;
        this.issuer = issuer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
