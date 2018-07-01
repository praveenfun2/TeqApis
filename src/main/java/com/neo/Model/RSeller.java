/*
package com.neo.Model;

import com.neo.DatabaseModel.Users.Seller;

import java.util.ArrayList;
import java.util.List;

*
 * Created by Praveen Gupta on 5/13/2017.


public class RSeller extends RUser{

    private String description, paypal;
    private Integer ltime;
    private boolean verified;

    private Integer distance;
    private Double lat, lng, rating;
    private Float price;

    private RAddress address;
    private List<RRating> reviews = new ArrayList<>();

    public RSeller() {
    }

    public RSeller(String name, String description, String sid, Float price, Integer ltime, Integer distance, Double rating) {
        this.name = name;
        this.description = description;
        this.id = sid;
        this.price = price;
        this.ltime = ltime;
        this.distance = distance;
        this.rating = rating;
    }

    public void buildRSeller(Seller seller) {
        name = seller.getName();
        description = seller.getDescription();
        id = seller.getId();
        if (seller.getRatingCount() == 0) rating = 0d;
        else rating = seller.getRating() / (double) seller.getRatingCount();
        price = seller.getPrice();
        ltime = seller.getLtime();
        paypal = seller.getPaypal();
        verified = seller.isValidated();
    }

    public void buildSeller(Seller seller) {
        seller.setName(name);
        seller.setDescription(description);
        seller.setPrice(price);
        seller.setLtime(ltime);
        seller.setPaypal(paypal);
    }

    public void addReview(String review, String reviewer, int rating) {
        reviews.add(new RRating(review, reviewer, rating));
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public RAddress getAddress() {
        return address;
    }

    public void setAddress(RAddress address) {
        this.address = address;
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

    public Integer getLtime() {
        return ltime;
    }

    public void setLtime(Integer ltime) {
        this.ltime = ltime;
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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
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
}
*/
