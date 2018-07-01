package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.Customer;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 5/12/2017.
 */
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String addressLine, city, state, country, postalCode;

    @Column(nullable = false)
    private Double lat, lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Customer customer;

    public Address(String addressLine, String city, String state, String country, String postalCode, Double lat, Double lng) {
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.lat = lat;
        this.lng = lng;
    }

    public Address() {
    }

    public Address(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
}
