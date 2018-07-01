package com.neo.DatabaseModel.Shipment;

import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.*;

@Entity
@DiscriminatorValue(UserDeliveryCoverage.SELLER+"")
public class SellerDeliveryCoverage extends UserDeliveryCoverage {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

    public SellerDeliveryCoverage() {
    }

    public SellerDeliveryCoverage(float price, int coverage, Seller seller) {
        super(price, coverage);
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
