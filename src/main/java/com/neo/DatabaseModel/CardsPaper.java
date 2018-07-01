package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Card.SellerCardFront;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity(name = "cards_paper")
public class CardsPaper {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "cid", nullable = false)
    private SellerCardFront card;

    @ManyToOne
    @JoinColumn(name = "ps_id", nullable = false)
    private PaperSeller paper;

    public CardsPaper() {
    }

    public CardsPaper(SellerCardFront card, PaperSeller paper) {
        this.card = card;
        this.paper = paper;
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

    public PaperSeller getPaper() {
        return paper;
    }

    public void setPaper(PaperSeller paper) {
        this.paper = paper;
    }
}
