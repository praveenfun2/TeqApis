package com.neo.DatabaseModel.Shipment;

import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.*;

@Entity
@DiscriminatorValue(UserDeliveryType.SELLER+"")
public class SellerDeliveryType extends UserDeliveryType {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

    public SellerDeliveryType() {
    }

    public SellerDeliveryType(DeliveryType deliveryType, float price, Seller seller) {
        super(deliveryType, price);
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
