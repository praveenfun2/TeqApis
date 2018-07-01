package com.neo.DatabaseModel.Approval;

import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Approval.CATEGORY +"")
public class CategoryApproval extends Approval{

    public CategoryApproval(Seller seller, String name) {
        super(seller, name);
    }

    public CategoryApproval() {
    }
}
