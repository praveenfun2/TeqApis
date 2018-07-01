package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */
@Entity(name = "advertisement_seller")
public class AdvertisementSeller {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Seller seller;

    @Column(nullable = false)
    private String name, poster;

    @Column
    private String areaName;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private long sTimestamp, eTimestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PaymentOrder paymentOrder;

    public AdvertisementSeller(Seller seller, String name, String poster, int type, long sTimeStamp, long eTimeStamp) {
        this.name = name;
        this.poster = poster;
        this.seller=seller;
        this.type = type;
        this.sTimestamp = sTimeStamp;
        this.eTimestamp = eTimeStamp;
    }

    public AdvertisementSeller() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public PaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(PaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getsTimestamp() {
        return sTimestamp;
    }

    public void setsTimestamp(long sTimestamp) {
        this.sTimestamp = sTimestamp;
    }

    public long geteTimestamp() {
        return eTimestamp;
    }

    public void seteTimestamp(long eTimestamp) {
        this.eTimestamp = eTimestamp;
    }
}