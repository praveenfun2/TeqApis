package com.neo.Model;

/**
 * Created by Praveen Gupta on 6/10/2017.
 */
public class RCardOrder extends ROrder {
    private String address, fCardImage, bCardImage, time, sTime;
    private float amt;
    private Long addressId;

    private RUser seller, customer;
    private RCourierOrder courierOrder;

    public RCardOrder(String address, String status, String fCardImage, String bCardImage, String time, String sTime, float amt, Long orderId, Boolean canCancel,
                      Boolean canComplain, Boolean canContact, Boolean canChangeStatus, Boolean canReview) {
        this.fCardImage = fCardImage;
        this.bCardImage = bCardImage;
        this.time = time;
        this.sTime = sTime;
        this.id = orderId;
        this.address = address;
        this.status = status;
        this.amt = amt;
        this.canCancel = canCancel;
        this.canComplain = canComplain;
        this.canContact = canContact;
        this.canChangeStatus = canChangeStatus;
        this.canReview = canReview;
    }

    public RCardOrder() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    public Boolean getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(Boolean canCancel) {
        this.canCancel = canCancel;
    }

    public Boolean getCanComplain() {
        return canComplain;
    }

    public void setCanComplain(Boolean canComplain) {
        this.canComplain = canComplain;
    }

    public RUser getSeller() {
        return seller;
    }

    public void setSeller(RUser seller) {
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RUser getCustomer() {
        return customer;
    }

    public void setCustomer(RUser customer) {
        this.customer = customer;
    }

    public String getfCardImage() {
        return fCardImage;
    }

    public void setfCardImage(String fCardImage) {
        this.fCardImage = fCardImage;
    }

    public Boolean getCanContact() {
        return canContact;
    }

    public void setCanContact(Boolean canContact) {
        this.canContact = canContact;
    }

    public String getbCardImage() {
        return bCardImage;
    }

    public void setbCardImage(String bCardImage) {
        this.bCardImage = bCardImage;
    }

    public RCourierOrder getCourierOrder() {
        return courierOrder;
    }

    public void setCourierOrder(RCourierOrder courierOrder) {
        this.courierOrder = courierOrder;
    }

    public Boolean getCanChangeStatus() {
        return canChangeStatus;
    }

    public void setCanChangeStatus(Boolean canChangeStatus) {
        this.canChangeStatus = canChangeStatus;
    }

    public Boolean getCanReview() {
        return canReview;
    }

    public void setCanReview(Boolean canReview) {
        this.canReview = canReview;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }
}
