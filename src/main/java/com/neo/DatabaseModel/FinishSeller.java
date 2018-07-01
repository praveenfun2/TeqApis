package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity(name = "finish_seller")
public class FinishSeller {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sid", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "fid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Finish finish;

    @Column(nullable = false)
    private float price;

    public FinishSeller(Seller seller, Finish finish, float price) {
        this.seller = seller;
        this.finish = finish;
        this.price = price;
    }

    public FinishSeller() {
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Finish getFinish() {
        return finish;
    }

    public void setFinish(Finish finish) {
        this.finish = finish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
