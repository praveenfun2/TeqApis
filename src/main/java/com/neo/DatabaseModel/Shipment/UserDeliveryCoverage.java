package com.neo.DatabaseModel.Shipment;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
public class UserDeliveryCoverage {
    public static final int CITY=1, STATE=2, COUNTRY=3, INTERNATIONAL=4;
    public static final int COURIER=1, SELLER=2;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int coverage;

    public UserDeliveryCoverage(float price, int coverage) {
        this.price = price;
        this.coverage = coverage;
    }

    public UserDeliveryCoverage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }
}
