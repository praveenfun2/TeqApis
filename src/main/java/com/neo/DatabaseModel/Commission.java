package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity
public class Commission {

    public static final int SELLER_TYPE=1, DESIGNER_TYPE=2, COURIER_TYPE=3;

    @Id
    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private float amt;

    public Commission(int type, float amt) {
        this.type = type;
        this.amt = amt;
    }

    public Commission() {
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
