package com.neo.Model;

/**
 * Created by localadmin on 2/7/17.
 */
public class RCommission {
    private Float seller, designer, courier;

    public RCommission(Float seller, Float designer, Float courier) {
        this.seller = seller;
        this.designer = designer;
        this.courier = courier;
    }

    public RCommission() {
    }

    public Float getSeller() {
        return seller;
    }

    public void setSeller(Float seller) {
        this.seller = seller;
    }

    public Float getDesigner() {
        return designer;
    }

    public void setDesigner(Float designer) {
        this.designer = designer;
    }

    public Float getCourier() {
        return courier;
    }

    public void setCourier(Float courier) {
        this.courier = courier;
    }
}
