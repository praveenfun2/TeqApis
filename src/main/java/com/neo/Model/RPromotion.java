package com.neo.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by localadmin on 9/7/17.
 */
public class RPromotion{

    private List<Long> cids;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date edate, sdate;
    private float discount;

    public RPromotion() {
    }

    public List<Long> getCids() {
        return cids;
    }

    public void setCids(List<Long> cids) {
        this.cids = cids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
