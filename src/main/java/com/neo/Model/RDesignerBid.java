package com.neo.Model;

/**
 * Created by Praveen Gupta on 5/19/2017.
 */
public class RDesignerBid {

    private Long id;
    private RDesignerOffer offer;
    private Float amt;
    private Integer etime;
    private String did;

    public RDesignerBid() {
    }

    public RDesignerBid(Long id, Float amt, Integer etime, String did) {
        this.id = id;
        this.amt = amt;
        this.etime = etime;
        this.did = did;
    }

    public RDesignerBid(Long id, Float amt, Integer etime) {
        this.id = id;
        this.amt = amt;
        this.etime = etime;
    }

    public RDesignerBid(Long id, RDesignerOffer designerOffer, Float amt, Integer etime, String did) {
        this.id = id;
        this.offer = designerOffer;
        this.amt = amt;
        this.etime = etime;
        this.did = did;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtime() {
        return etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Float getAmt() {
        return amt;
    }

    public void setAmt(Float amt) {
        this.amt = amt;
    }

    public RDesignerOffer getOffer() {
        return offer;
    }

    public void setOffer(RDesignerOffer offer) {
        this.offer = offer;
    }
}
