package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.Designer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity(name = "designer_bid")
public class DesignerBid {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private DesignerOffer offer;

    @Column(nullable = false)
    private Float amt;

    @Column(nullable = false)
    private String duration;

    @ManyToOne
    @JoinColumn(name = "did")
    private Designer designer;

    public DesignerBid() {
    }

    public DesignerBid(DesignerOffer offer, Float amt, String duration, Designer designer) {
        this.offer = offer;
        this.amt = amt;
        this.duration = duration;
        this.designer = designer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public DesignerOffer getOffer() {
        return offer;
    }

    public void setOffer(DesignerOffer offer) {
        this.offer = offer;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Float getAmt() {
        return amt;
    }

    public void setAmt(Float amt) {
        this.amt = amt;
    }
}
