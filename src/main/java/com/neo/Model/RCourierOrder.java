package com.neo.Model;

public class RCourierOrder extends ROrder {

    private String courierId, name;
    private Long serviceType;
    private String deliveryAddress;
    private Price bill;
    private boolean canBook, booked;

    public RCourierOrder(Long id, String status, String deliveryAddress, Price bill, boolean canCancel,
                         boolean canReview, boolean canComplain, boolean canContact, boolean canChangeStatus) {
        this.id = id;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.bill = bill;
        this.canCancel = canCancel;
        this.canReview = canReview;
        this.canComplain = canComplain;
        this.canContact = canContact;
        this.canChangeStatus = canChangeStatus;
    }

    public RCourierOrder(Long id, String name, Price bill, boolean canBook,boolean canCancel,
                         boolean canReview, boolean canComplain, boolean canContact) {
        this.id = id;
        this.name=name;
        this.canBook=canBook;
        this.bill = bill;
        this.canCancel = canCancel;
        this.canReview = canReview;
        this.canComplain = canComplain;
        this.canContact = canContact;
    }

    public RCourierOrder(boolean canBook, boolean booked) {
        this.canBook = canBook;
        this.booked = booked;
    }

    public RCourierOrder() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Price getBill() {
        return bill;
    }

    public void setBill(Price bill) {
        this.bill = bill;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    public boolean isCanComplain() {
        return canComplain;
    }

    public void setCanComplain(boolean canComplain) {
        this.canComplain = canComplain;
    }

    public boolean isCanContact() {
        return canContact;
    }

    public void setCanContact(boolean canContact) {
        this.canContact = canContact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public boolean isCanChangeStatus() {
        return canChangeStatus;
    }

    public void setCanChangeStatus(boolean canChangeStatus) {
        this.canChangeStatus = canChangeStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanBook() {
        return canBook;
    }

    public void setCanBook(boolean canBook) {
        this.canBook = canBook;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Long getServiceType() {
        return serviceType;
    }

    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }
}
