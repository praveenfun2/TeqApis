package com.neo.DatabaseModel.Card;

import com.neo.DatabaseModel.Users.Seller;
import com.neo.DatabaseModel.Users.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */

@Entity
@DiscriminatorValue(value = Card.SELLER_CARD_BACK+"")
public class SellerCardBack extends SellerCard {

    public SellerCardBack(float price, boolean layout, Seller user, String name) {
        super(price, layout, user, name);
    }

    public SellerCardBack() {
    }
}
