package com.neo.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Praveen Gupta on 6/3/2017.
 */
public class RSellerAdvertisement extends RPaymentInfo {
    private String name, poster;
    private int type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sdate, edate;

    public RSellerAdvertisement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

}
