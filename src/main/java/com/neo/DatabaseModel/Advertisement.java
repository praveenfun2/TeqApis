package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */
@Entity
public class Advertisement {

    public static final int CITY_TYPE = 1, STATE_TYPE = 2, COUNTRY_TYPE = 3, INTERNATIONAL_TYPE=4;

    @Id
    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private float amt;

    public Advertisement(int type, float amt) {
        this.type = type;
        this.amt = amt;
    }

    public Advertisement() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }
}
