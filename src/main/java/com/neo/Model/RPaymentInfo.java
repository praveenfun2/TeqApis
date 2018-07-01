package com.neo.Model;

/**
 * Created by Praveen Gupta on 6/3/2017.
 */
public abstract class RPaymentInfo {
    protected String paymentID, payerID;

    public RPaymentInfo() {
    }

    public RPaymentInfo(String paymentID, String payerID) {
        this.paymentID = paymentID;
        this.payerID = payerID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPayerID() {
        return payerID;
    }

    public void setPayerID(String payerID) {
        this.payerID = payerID;
    }
}
