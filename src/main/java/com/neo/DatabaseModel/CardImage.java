package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.Card;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class CardImage {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String fileName;

    @JoinColumn(nullable = false)
    @ManyToOne
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Card card;

    @Column
    private String color;

    public CardImage(String fileName, Card card, String color) {
        this.fileName = fileName;
        this.card = card;
        this.color = color;
    }

    public CardImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
