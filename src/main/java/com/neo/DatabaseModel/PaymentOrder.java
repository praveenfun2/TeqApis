package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Praveen Gupta on 6/11/2017.
 */
@Entity
public class PaymentOrder {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String saleId;

    public PaymentOrder() {
    }

    public PaymentOrder(String saleId) {
        this.saleId = saleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }
}
