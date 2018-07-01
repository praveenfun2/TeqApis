package com.neo.DatabaseModel.Shipment;

import com.neo.DatabaseModel.Users.Courier;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = UserDeliveryType.COURIER+"")
public class CourierDeliveryType extends UserDeliveryType{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Courier courier;

    public CourierDeliveryType(DeliveryType deliveryType, float price, Courier courier) {
        super(deliveryType, price);
        this.courier = courier;
    }

    public CourierDeliveryType() {
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
