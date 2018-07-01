package com.neo.Model;

/**
 * Created by Praveen Gupta on 5/12/2017.
 */
public abstract class ROrder extends RPaymentInfo {
    protected boolean canCancel, canComplain, canContact, canChangeStatus, canReview;
    protected Long id;
    protected String status;

    public ROrder() {
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
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

    public boolean isCanChangeStatus() {
        return canChangeStatus;
    }

    public void setCanChangeStatus(boolean canChangeStatus) {
        this.canChangeStatus = canChangeStatus;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
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
}
