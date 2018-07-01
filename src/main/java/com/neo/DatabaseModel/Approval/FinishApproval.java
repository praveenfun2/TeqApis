package com.neo.DatabaseModel.Approval;

import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Approval.FINISH + "")
public class FinishApproval extends PaperApproval {

    public FinishApproval() {
    }

    public FinishApproval(Seller seller, String name, String description) {
        super(seller, name, description);
    }
}
