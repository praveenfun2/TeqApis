package com.neo.Model;

/**
 * Created by Praveen Gupta on 6/12/2017.
 */
public class RDesignerOrder extends ROrder {
    private RDesignerBid bid;
    private RDesignerOffer offer;
    private String img;

    public RDesignerOrder() {
    }

    public RDesignerOrder(RDesignerBid bid, RDesignerOffer offer, Long id, String status, String img, Boolean canCancel,
                          Boolean canComplain, Boolean canContact, Boolean canChangeStatus, Boolean canReview) {
        this.bid = bid;
        this.offer = offer;
        this.id = id;
        this.status = status;
        this.img = img;
        this.canCancel = canCancel;
        this.canComplain = canComplain;
        this.canContact = canContact;
        this.canChangeStatus = canChangeStatus;
        this.canReview = canReview;
    }

    public RDesignerBid getBid() {
        return bid;
    }

    public void setBid(RDesignerBid bid) {
        this.bid = bid;
    }

    public RDesignerOffer getOffer() {
        return offer;
    }

    public void setOffer(RDesignerOffer offer) {
        this.offer = offer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
