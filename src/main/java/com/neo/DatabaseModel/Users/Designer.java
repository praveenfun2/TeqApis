package com.neo.DatabaseModel.Users;

import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.DesignerBid;
import com.neo.DatabaseModel.DesignerOffer;
import com.neo.DatabaseModel.Order.DesignerOrder;
import com.neo.DatabaseModel.Order.Orders;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity
@DiscriminatorValue(value = User.DESIGNER_TYPE)
public class Designer extends User{

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToMany(mappedBy = "designer")
    private List<DesignerBid> bids;

    @OneToMany
    @JoinColumn(name = "provider_id")
    @Where(clause = "type="+ Orders.DESIGNER)
    private List<DesignerOrder> orders;

    @Column
    private boolean validated=false;

    @Column
    private String paypal;

    public Designer() {
    }

    public Designer(String id, String pass, String name, int otp) {
        super(id, pass, name, otp);
    }

    public List<DesignerBid> getBids() {
        return bids;
    }

    public void setBids(List<DesignerBid> bids) {
        this.bids = bids;
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

    public List<DesignerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<DesignerOrder> orders) {
        this.orders = orders;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
