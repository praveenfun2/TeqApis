package com.neo.Model;

import com.neo.DatabaseModel.Shipment.UserDeliveryType;

import java.util.ArrayList;
import java.util.List;

public class RCourier {

    private String name, description, id, paypal, phone;
    private boolean verified;

    private List<RCoverage> coverages = new ArrayList<>();
    private List<RDeliveryType> deliveryTypes = new ArrayList<>();

    private Distance distance;
    private Double lat, lng, rating;
    private Price bill;

    private RAddress address;
    private String addressString;

    public RCourier() {
    }

    public RCourier(String id, String name, String description, Double rating, Price bill, String addressString) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.bill = bill;
        this.addressString = addressString;
    }

    public RCourier(String name, String description, String id, String paypal, boolean verified, Double rating) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.paypal = paypal;
        this.verified = verified;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public RAddress getAddress() {
        return address;
    }

    public void setAddress(RAddress address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Price getBill() {
        return bill;
    }

    public void setBill(Price bill) {
        this.bill = bill;
    }

    public void addDeliveryType(UserDeliveryType cdt) {
        deliveryTypes.add(new RDeliveryType(cdt.getDeliveryType().getTitle(), cdt.getDeliveryType().getDescription(), cdt.getPrice()));
    }

    public List<RCoverage> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<RCoverage> coverages) {
        this.coverages = coverages;
    }

    public List<RDeliveryType> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(List<RDeliveryType> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }
}
