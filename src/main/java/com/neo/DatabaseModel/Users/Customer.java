package com.neo.DatabaseModel.Users;

import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.CustomerCard;
import com.neo.DatabaseModel.CartItem;
import com.neo.DatabaseModel.DesignerOffer;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.DesignerOrder;
import com.neo.DatabaseModel.Order.Orders;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by localadmin on 18/6/17.
 */
@Entity
@DiscriminatorValue(value = User.CUSTOMER_TYPE)
public class Customer extends User {

    @OneToMany
    @JoinColumn(name = "uid")
    @Where(clause = "type=" + Card.CUSTOMER_CARD)
    private List<CustomerCard> cards;

    @OneToMany(mappedBy = "customer")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "customer")
    private List<DesignerOffer> offers;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany
    @JoinColumn(name = "consumer_id")
    @Where(clause = "type=" + Orders.CARD)
    private List<CardOrder> cardOrders;

    @OneToMany
    @JoinColumn(name = "consumer_id")
    @Where(clause = "type=" + Orders.DESIGNER)
    private List<DesignerOrder> designerOrders;

    public Customer() {
    }

    public Customer(String id, String pass, String name, int otp) {
        super(id, pass, name, otp);
    }

    public List<CustomerCard> getCards() {
        return cards;
    }

    public void setCards(List<CustomerCard> cards) {
        this.cards = cards;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<DesignerOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<DesignerOffer> offers) {
        this.offers = offers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<CardOrder> getCardOrders() {
        return cardOrders;
    }

    public void setCardOrders(List<CardOrder> cardOrders) {
        this.cardOrders = cardOrders;
    }

    public List<DesignerOrder> getDesignerOrders() {
        return designerOrders;
    }

    public void setDesignerOrders(List<DesignerOrder> designerOrders) {
        this.designerOrders = designerOrders;
    }
}
