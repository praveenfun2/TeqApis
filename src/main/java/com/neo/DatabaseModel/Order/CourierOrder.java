package com.neo.DatabaseModel.Order;

import com.neo.DatabaseModel.PaymentOrder;
import com.neo.DatabaseModel.Rating;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = Orders.COURIER + "")
public class CourierOrder extends Orders {

    public static final int STATUS_RECEIVED_KEY = 4, STATUS_PICKED_UP_KEY = 5, STATUS_DISPATCHED_KEY = 6, STATUS_DELIVERED_KEY = 7,
            STATUS_CANCELED_KEY = -1;

    @ManyToOne
    @JoinColumn
    private CardOrder cardOrder;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Courier courier;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Rating sellerRating, courierRating;

    public CourierOrder() {
    }

    public CourierOrder(Float price, PaymentOrder paymentOrder, CardOrder cardOrder, Seller seller, Courier courier) {
        this.price=price;
        this.paymentOrder=paymentOrder;
        this.seller=seller;
        this.courier=courier;
        this.cardOrder = cardOrder;
        this.status=STATUS_RECEIVED_KEY;
    }

    public CardOrder getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(CardOrder cardOrder) {
        this.cardOrder = cardOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Rating getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Rating sellerRating) {
        this.sellerRating = sellerRating;
    }

    public Rating getCourierRating() {
        return courierRating;
    }

    public void setCourierRating(Rating courierRating) {
        this.courierRating = courierRating;
    }
}
