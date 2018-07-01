package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "paper_seller")
public class PaperSeller {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sid", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "pqid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Paper paper;

    @Column(nullable = false)
    private float price;

    public PaperSeller(Seller seller, Paper paper, float price) {
        this.seller = seller;
        this.paper = paper;
        this.price = price;
    }

    public PaperSeller() {
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
