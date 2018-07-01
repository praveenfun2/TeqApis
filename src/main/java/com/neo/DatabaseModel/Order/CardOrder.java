package com.neo.DatabaseModel.Order;

import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.*;

/**
 * Created by localadmin on 17/6/17.
 */

@Entity
@DiscriminatorValue(value = Orders.CARD +"")
public class CardOrder extends Orders {

    public static final int STATUS_RECEIVED_KEY=1, STATUS_PROGRESS_KEY=2, STATUS_PRINTED_KEY=3,
            STATUS_CANCELED_KEY=-1;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn
    private Address address;

    @Column
    private String fCardImage, bCardImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fs_id")
    private FinishSeller finishSeller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_id")
    private PaperSeller paperSeller;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private CourierOrder courierOrder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Rating sellerRating, customerRating;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Seller seller;

    public CardOrder() {

    }

    public CardOrder(int quantity, float price, Address address, PaymentOrder paymentOrder, Seller seller, String fCardImage,
                     FinishSeller finishSeller, PaperSeller paperSeller, Customer customer) {
        this.paymentOrder=paymentOrder;
        this.price=price;
        this.customer=customer;
        this.seller=seller;
        this.quantity = quantity;
        this.address = address;
        this.fCardImage = fCardImage;
        this.finishSeller = finishSeller;
        this.paperSeller = paperSeller;
        this.status=STATUS_RECEIVED_KEY;
    }

    public FinishSeller getFinishSeller() {
        return finishSeller;
    }

    public void setFinishSeller(FinishSeller finishSeller) {
        this.finishSeller = finishSeller;
    }

    public PaperSeller getPaperSeller() {
        return paperSeller;
    }

    public void setPaperSeller(PaperSeller paperSeller) {
        this.paperSeller = paperSeller;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getfCardImage() {
        return fCardImage;
    }

    public void setfCardImage(String fCardImage) {
        this.fCardImage = fCardImage;
    }

    public String getbCardImage() {
        return bCardImage;
    }

    public void setbCardImage(String bCardImage) {
        this.bCardImage = bCardImage;
    }

    public CourierOrder getCourierOrder() {
        return courierOrder;
    }

    public void setCourierOrder(CourierOrder courierOrder) {
        this.courierOrder = courierOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Rating getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Rating sellerRating) {
        this.sellerRating = sellerRating;
    }

    public Rating getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Rating customerRating) {
        this.customerRating = customerRating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
