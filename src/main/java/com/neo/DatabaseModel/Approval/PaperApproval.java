package com.neo.DatabaseModel.Approval;

import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Approval.PAPER +"")
public class PaperApproval extends Approval {

    @Column
    private String description;

    public PaperApproval(Seller seller, String name, String description) {
        super(seller, name);
        this.description = description;
    }

    public PaperApproval() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
