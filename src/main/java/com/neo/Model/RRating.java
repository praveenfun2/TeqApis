package com.neo.Model;

public class RRating {
    private Long orderId;
    private String review="", reviewer;
    private Integer rating;

    public RRating(String review, String reviewer, Integer rating) {
        this.review = review;
        this.reviewer = reviewer;
        this.rating = rating;
    }

    public RRating() {
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
