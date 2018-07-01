package com.neo.Model;

import com.neo.DatabaseModel.Address;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

/**
 * Created by localadmin on 21/6/17.
 */
public class RAddress {
    private Long id;
    private String addressString;
    private String name;
    private String addressLine;
    private String city;
    private String state, country;
    private String phone;
    private String postalCode;

    public RAddress() {
    }

    public RAddress(String addressString, String name, String addressLine, String city, String state, String country, String phone, String postalCode) {
        this.addressString = addressString;
        this.name = name;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.postalCode = postalCode;
    }

    public RAddress(String addressString, String addressLine, String city, String state, String country, String phone, String postalCode) {
        this.addressString = addressString;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.postalCode = postalCode;
    }

    public RAddress(String addressString, String name, String phone, String postalCode) {
        this.addressString = addressString;
        this.name = name;
        this.phone = phone;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
