package com.neo.DatabaseModel.Card;

import com.neo.DatabaseModel.CardsFinish;
import com.neo.DatabaseModel.CardsPaper;
import com.neo.DatabaseModel.CardsSubcat;
import com.neo.DatabaseModel.Category;
import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */
@Entity
@DiscriminatorValue(value = Card.SELLER_CARD_FRONT + "")
public class SellerCardFront extends SellerCard {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catid")
    private Category category;

    @OneToMany(mappedBy = "card", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardsSubcat> subcats;

    @OneToMany(mappedBy = "card", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardsPaper> papers;

    @OneToMany(mappedBy = "card", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardsFinish> finishes;

    {
        subcats = new ArrayList<>();
        papers = new ArrayList<>();
        finishes = new ArrayList<>();
    }

    public SellerCardFront(float price, boolean layout, Seller user, String name, Category category) {
        super(price, layout, user, name);
        this.category = category;
    }

    public SellerCardFront() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CardsSubcat> getSubcats() {
        return subcats;
    }

    public void setSubcats(List<CardsSubcat> subcats) {
        this.subcats = subcats;
    }

    public List<CardsPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<CardsPaper> papers) {
        this.papers = papers;
    }

    public List<CardsFinish> getFinishes() {
        return finishes;
    }

    public void setFinishes(List<CardsFinish> finishes) {
        this.finishes = finishes;
    }


}
