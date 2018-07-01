package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity
public class Promotion {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "promotion")
    @Where(clause = "type=" + Card.SELLER_CARD_FRONT + " or type=" + Card.SELLER_CARD_BACK)
    private List<SellerCard> cards;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float discount;

    @Column(nullable = false)
    private long sTimeStamp, eTimeStamp;

    public Promotion() {
    }

    public Promotion(String name, Float discount, long sTimeStamp, long eTimeStamp) {
        this.name = name;
        this.discount = discount;
        this.sTimeStamp = sTimeStamp;
        this.eTimeStamp = eTimeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getsTimeStamp() {
        return sTimeStamp;
    }

    public void setsTimeStamp(long sTimeStamp) {
        this.sTimeStamp = sTimeStamp;
    }

    public long geteTimeStamp() {
        return eTimeStamp;
    }

    public void seteTimeStamp(long eTimeStamp) {
        this.eTimeStamp = eTimeStamp;
    }

    public List<SellerCard> getCards() {
        return cards;
    }

    public void setCards(List<SellerCard> cards) {
        this.cards = cards;
    }
}
