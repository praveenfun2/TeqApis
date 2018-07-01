package com.neo.Model;

import com.neo.DatabaseModel.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 5/15/2017.
 */
public class RUser {
    private int otp;
    private String id, type, name, password, phone;
    private boolean activated;
    private String description, paypal;
    private Integer ltime;
    private boolean verified;

    private Integer distance;
    private Double lat, lng, rating;
    private Float price;

    private RAddress address;
    private List<RRating> reviews = new ArrayList<>();

    public RUser(String id) {
        this.id = id;
    }

    public RUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RUser(String id, String name, String paypal) {
        this.id = id;
        this.name = name;
        this.paypal = paypal;
    }

    public RUser() {
    }

    public void addReview(String review, String reviewer, int rating) {
        reviews.add(new RRating(review, reviewer, rating));
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<RRating> getReviews() {
        return reviews;
    }

    public void setReviews(List<RRating> reviews) {
        this.reviews = reviews;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RAddress getAddress() {
        return address;
    }

    public void setAddress(RAddress address) {
        this.address = address;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public Integer getLtime() {
        return ltime;
    }

    public void setLtime(Integer ltime) {
        this.ltime = ltime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
