package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Card.SellerCardFront;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity(name = "cards_subcat")
public class CardsSubcat {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "cid", nullable = false)
    private SellerCardFront card;

    @ManyToOne
    @JoinColumn(name = "subid", nullable = false)
    private SubCategory subCategory;

    public CardsSubcat() {
    }

    public CardsSubcat(SellerCardFront card, SubCategory subCategory) {
        this.card = card;
        this.subCategory = subCategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SellerCardFront getCard() {
        return card;
    }

    public void setCard(SellerCardFront card) {
        this.card = card;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}

