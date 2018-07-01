package com.neo.DatabaseModel.Users;

import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.SellerCardBack;
import com.neo.DatabaseModel.Card.SellerCardFront;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Shipment.DeliveryType;
import com.neo.DatabaseModel.Shipment.SellerDeliveryType;
import com.neo.DatabaseModel.Shipment.UserDeliveryType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = User.SELLER_TYPE)
public class Seller extends User{

    @Column(length = 1024)
    private String description;

    @Column
    private Integer ltime=null;

    @Column
    private Float price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToMany(mappedBy = "seller")
    private List<PaperSeller> papers;

    @OneToMany(mappedBy = "seller")
    private List<FinishSeller> finishes;

    @OneToMany
    @JoinColumn(name = "sid")
    @Where(clause = "type="+Card.SELLER_CARD_FRONT)
    private List<SellerCardFront> frontCards;

    @OneToMany
    @JoinColumn(name = "sid")
    @Where(clause = "type="+Card.SELLER_CARD_BACK)
    private List<SellerCardBack> backCards;

    @OneToMany(mappedBy = "seller")
    private List<AdvertisementSeller> advertisements;

    @OneToMany
    @JoinColumn(name = "provider_id")
    @Where(clause = "type="+ Orders.CARD)
    private List<CardOrder> orders;

    @OneToMany
    @JoinColumn(name = "user_id")
    @Where(clause = "type="+ UserDeliveryType.SELLER)
    private List<SellerDeliveryType> deliveryTypes;

    @Column
    private String paypal;

    public Seller() {
    }

    public Seller(String id, String pass, String name, int otp) {
        super(id, pass, name, otp);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PaperSeller> getPapers() {
        return papers;
    }

    public void setPapers(List<PaperSeller> papers) {
        this.papers = papers;
    }

    public List<FinishSeller> getFinishes() {
        return finishes;
    }

    public void setFinishes(List<FinishSeller> finishes) {
        this.finishes = finishes;
    }

    public List<SellerCardFront> getFrontCards() {
        return frontCards;
    }

    public void setFrontCards(List<SellerCardFront> frontCards) {
        this.frontCards = frontCards;
    }

    public Integer getLtime() {
        return ltime;
    }

    public void setLtime(Integer ltime) {
        this.ltime = ltime;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<AdvertisementSeller> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<AdvertisementSeller> advertisements) {
        this.advertisements = advertisements;
    }

    public List<SellerCardBack> getBackCards() {
        return backCards;
    }

    public void setBackCards(List<SellerCardBack> backCards) {
        this.backCards = backCards;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<CardOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CardOrder> orders) {
        this.orders = orders;
    }

    public List<SellerDeliveryType> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(List<SellerDeliveryType> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }
}
