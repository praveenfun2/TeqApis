package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Users.Customer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 5/11/2017.
 */

@Entity
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fitemid")
    private Item fItem;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="bitemid")
    private Item bItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fs_id")
    private FinishSeller finishSeller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_id")
    private PaperSeller paperSeller;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Customer customer;

    public CartItem() {
    }

    public CartItem(Customer customer) {
        this.customer = customer;
    }

    public CartItem(Item fItem, Item bItem, Customer customer) {
        this.fItem = fItem;
        this.bItem = bItem;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinishSeller getFinishSeller() {
        return finishSeller;
    }

    public void setFinishSeller(FinishSeller finishSeller) {
        this.finishSeller = finishSeller;
    }

    public PaperSeller getPaperSeller() {
        return paperSeller;
    }

    public void setPaperSeller(PaperSeller paperSeller) {
        this.paperSeller = paperSeller;
    }

    public Item getfItem() {
        return fItem;
    }

    public void setfItem(Item fItem) {
        this.fItem = fItem;
    }

    public Item getbItem() {
        return bItem;
    }

    public void setbItem(Item bItem) {
        this.bItem = bItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
