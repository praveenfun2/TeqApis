package com.neo.DatabaseModel.Users;

import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Shipment.CourierDeliveryCoverage;
import com.neo.DatabaseModel.Shipment.CourierDeliveryType;
import com.neo.DatabaseModel.Shipment.UserDeliveryType;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Order.Orders;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = User.COURIER_TYPE)
public class Courier extends User {

    public static final int CITY_COVERAGE_KEY=1, STATE_COVERAGE_KEY=2, COUNTRY_COVERAGE_KEY=3, INTERNATIONAL_COVERAGE_KEY=4;

    @Column(length=1024)
    private String description;

    @Column
    private Double lat, lon;

    @OneToMany(orphanRemoval = true, mappedBy = "courier", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<CourierDeliveryCoverage> coverages;

    @OneToMany(orphanRemoval = true, mappedBy = "courier", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<CourierDeliveryType> deliveryTypes;

    @Column
    private boolean validated = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    @Column
    private String paypal;

    @Column
    private int coverage;

    @OneToMany
    @JoinColumn(name = "provider_id")
    @Where(clause = "type=" + Orders.COURIER)
    private List<CourierOrder> orders;

    public Courier() {
    }

    public Courier(String id, String pass, String name, int otp) {
        super(id, pass, name, otp);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public List<CourierOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CourierOrder> orders) {
        this.orders = orders;
    }

    public List<CourierDeliveryCoverage> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<CourierDeliveryCoverage> coverages) {
        this.coverages = coverages;
    }

    public List<CourierDeliveryType> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(List<CourierDeliveryType> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }
}
