package com.neo.DatabaseModel.Card;

import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.DatabaseModel.Users.User;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 5/13/2017.
 */
@Entity
@DiscriminatorValue(value = Card.CUSTOMER_CARD +"")
public class CustomerCard extends Card {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Customer customer;

    public CustomerCard(boolean landscape, String name, Customer customer) {
        super(landscape, name);
        this.customer=customer;
    }

    public CustomerCard() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
