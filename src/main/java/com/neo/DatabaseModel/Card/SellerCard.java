package com.neo.DatabaseModel.Card;

import com.neo.DatabaseModel.Promotion;
import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public abstract class SellerCard extends Card {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promo_id")
    private Promotion promotion;

    public SellerCard(float price, boolean layout, Seller seller, String name) {
        super(layout,price, name, seller);
    }

    public SellerCard() {

    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
