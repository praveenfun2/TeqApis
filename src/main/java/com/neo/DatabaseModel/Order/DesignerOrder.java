package com.neo.DatabaseModel.Order;

import com.neo.DatabaseModel.DesignerBid;
import com.neo.DatabaseModel.DesignerOffer;
import com.neo.DatabaseModel.PaymentOrder;
import com.neo.DatabaseModel.Rating;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Designer;
import com.neo.DatabaseModel.Users.User;
import sun.security.krb5.internal.crypto.Des;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Praveen Gupta on 6/12/2017.
 */
@Entity
@DiscriminatorValue(value = Orders.DESIGNER+"")
public class DesignerOrder extends Orders {

    public static final int STATUS_RECEIVED_KEY=1, STATUS_PROGRESS_KEY=2,
            STATUS_COMPLETED_KEY=3, STATUS_CANCELED_KEY=-1;
    public static final String STATUS_CANCELED = "canceled", STATUS_COMPLETED = "completed", STATUS_PROGRESS = "In progress",
            STATUS_RECEIVED = "received";

    public static HashMap<Integer, String> statusMap;
    static {
        statusMap=new HashMap<>();
        statusMap.put(STATUS_RECEIVED_KEY, STATUS_RECEIVED);
        statusMap.put(STATUS_PROGRESS_KEY, STATUS_PROGRESS);
        statusMap.put(STATUS_COMPLETED_KEY, STATUS_COMPLETED);
        statusMap.put(STATUS_CANCELED_KEY, STATUS_CANCELED);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private DesignerBid bid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Designer designer;

    @Column
    private String img;

    @OneToOne(fetch = FetchType.LAZY)
    private DesignerOffer designerOffer;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Rating customerRating, designerRating;


    public DesignerOrder(Float price, PaymentOrder paymentOrder, Customer customer, Designer designer) {
        super(price, paymentOrder);
        this.customer=customer;
        this.designer=designer;
        this.status=STATUS_RECEIVED_KEY;
    }

    public DesignerOrder() {

    }

    public DesignerOffer getDesignerOffer() {
        return designerOffer;
    }

    public void setDesignerOffer(DesignerOffer designerOffer) {
        this.designerOffer = designerOffer;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public DesignerBid getBid() {
        return bid;
    }

    public void setBid(DesignerBid bid) {
        this.bid = bid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Rating getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Rating customerRating) {
        this.customerRating = customerRating;
    }

    public Rating getDesignerRating() {
        return designerRating;
    }

    public void setDesignerRating(Rating designerRating) {
        this.designerRating = designerRating;
    }
}
