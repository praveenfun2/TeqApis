package com.neo.DatabaseModel.Shipment;

import com.neo.DatabaseModel.Users.Courier;

import javax.persistence.*;

@Entity
@DiscriminatorValue(UserDeliveryCoverage.COURIER+"")
public class CourierDeliveryCoverage extends UserDeliveryCoverage{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Courier courier;

    public CourierDeliveryCoverage(float price, int coverage, Courier courier) {
        super(price, coverage);
        this.courier = courier;
    }

    public CourierDeliveryCoverage() {
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
