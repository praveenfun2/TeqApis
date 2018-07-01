package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Card.SellerCardFront;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity(name = "cards_finish")
public class CardsFinish {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "cid", nullable = false)
    private SellerCardFront card;

    @ManyToOne
    @JoinColumn(name = "fs_id", nullable = false)
    private FinishSeller finish;

    public CardsFinish() {
    }

    public CardsFinish(SellerCardFront card, FinishSeller finishSeller) {
        this.card = card;
        this.finish = finishSeller;
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

    public FinishSeller getFinish() {
        return finish;
    }

    public void setFinish(FinishSeller finish) {
        this.finish = finish;
    }
}
