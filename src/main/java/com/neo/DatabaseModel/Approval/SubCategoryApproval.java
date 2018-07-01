package com.neo.DatabaseModel.Approval;

import com.neo.DatabaseModel.Category;
import com.neo.DatabaseModel.Users.Seller;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = Approval.SUB_CATEGORY +"")
public class SubCategoryApproval extends Approval{

    @ManyToOne
    private Category category;

    public SubCategoryApproval(Seller seller, String name, Category category) {
        super(seller, name);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
